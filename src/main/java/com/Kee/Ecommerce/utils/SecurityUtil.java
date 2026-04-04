package com.Kee.Ecommerce.utils;

import com.Kee.Ecommerce.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public Long getCurrentUserId(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || !authentication.isAuthenticated()){
            throw new RuntimeException("User Not authenticated");
        }
        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl)principal).getId();
        }
        else{
            throw new RuntimeException("Principal is  Not an instance of userDetailsImpl");
        }

    }
}
