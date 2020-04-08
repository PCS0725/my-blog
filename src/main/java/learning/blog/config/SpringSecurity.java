package learning.blog.config;

import learning.blog.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
/*
When we add spring security to our project as a dependency, we get a login page by default and all our pages are
protected by default. We extend WebSecurityConfigurerAdapater class to configure the security.
 */
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    /*
    We need to define a datasource and then the queries which spring security should perform when trying to authenticate
    the users. The default database is embedded database. We can create users with default roles and passwords.
    Or, we can set up in-memory users and roles to verify.
    If we want to authenticate by our own custom methods(JPA, another datasource) then we have to implement a UserDetails
    interface and provide it to spring security.
     */
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    //I could have autowired it but I need to create a bean of it. Only PasswordEncoder is defined as a bean by defalut.
    //Hence, I have to create an object and inject it by myself.
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public SpringSecurity(){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //Setting the method of authentication, the datasource. There are several versions of the configure method.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    //Use this methods to set up filter chains on urls, authorize roles, create a login form, etc.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("resources/**","/static/**","/css/**","/images/**","/assets/**","/js/**","/fonts/**","/webfonts/**").permitAll()
                .antMatchers("/","/home*","/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/home/**","/post*","/post/**","/createComment*","/search*","/pop/**","/searchCategory*").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .usernameParameter("user_name")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home").and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
//This method allows us to configure global security
//    @Override
//    public void configure(WebSecurity web) throws Exception {
        //spring security filter chain will ignore these paths.
//        web
//                .ignoring()
//                .antMatchers("/resources/**","/","/home*","/home","static/**", "css/**", "/js/**", "/images/**");
//    }
    //Creating and using a bean in same configuration file will create a circular dependency. If I create a
    //PasswordEncoder object within this file, then code will not compile.
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
