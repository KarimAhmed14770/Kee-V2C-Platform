package com.Kee.Ecommerce.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.Kee.Ecommerce.Repository.UserRepository;
import com.Kee.Ecommerce.entity.Credential;
import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class SecurityIntegrationTest {

    private MockMvc mockMvc;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityIntegrationTest(MockMvc mockMvc,UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.mockMvc=mockMvc;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
@BeforeEach
    void setUp() {
        // Clear the deck
        userRepository.deleteAll();

        // Create the Customer
        User customer = new User();
        customer.setCredential(new Credential("customer", passwordEncoder.encode("customer"), true));
        customer.addRole(new Role(UserRoles.ROLE_CUSTOMER));
        userRepository.save(customer);

        // Create the Admin
        User admin = new User();
        admin.setCredential(new Credential("admin", passwordEncoder.encode("admin"), true));
        admin.addRole(new Role(UserRoles.ROLE_ADMIN));
    admin.addRole(new Role(UserRoles.ROLE_CUSTOMER));
    admin.addRole(new Role(UserRoles.ROLE_SELLER));
        userRepository.save(admin);

        // Repeat for Seller...
    User seller = new User();
    seller.setCredential(new Credential("seller", passwordEncoder.encode("seller"), true));
    seller.addRole(new Role(UserRoles.ROLE_SELLER));
    seller.addRole(new Role(UserRoles.ROLE_CUSTOMER));
    userRepository.save(seller);
    }
    @Test
    void customerAccessingCustomerEndPointShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeCustomer")
                .with(httpBasic("customer","customer")))
                .andExpect(status().isOk());
    }

    @Test
    void customerAccessingSellerEndPointShouldFail() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeSeller")
                        .with(httpBasic("customer","customer")))
                .andExpect(status().isForbidden());
    }

    @Test
    void customerAccessingAdminEndPointShouldFail() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeAdmin")
                        .with(httpBasic("customer","customer")))
                .andExpect(status().isForbidden());
    }

    @Test
    void sellerAccessingCustomerEndPointShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeCustomer")
                        .with(httpBasic("seller","seller")))
                .andExpect(status().isOk());
    }

    @Test
    void sellerAccessingSellerEndPointShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeSeller")
                        .with(httpBasic("seller","seller")))
                .andExpect(status().isOk());
    }

    @Test
    void sellerAccessingAdminEndPointShouldFail() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeAdmin")
                        .with(httpBasic("seller","seller")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminAccessingCustomerEndPointShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeCustomer")
                        .with(httpBasic("admin","admin")))
                .andExpect(status().isOk());
    }

    @Test
    void adminAccessingSellerEndPointShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeSeller")
                        .with(httpBasic("admin","admin")))
                .andExpect(status().isOk());
    }
    @Test
    void adminAccessingAdminEndPointShouldSucceed() throws Exception {
        mockMvc.perform(get("/api/test/securityDBhandShakeAdmin")
                        .with(httpBasic("admin","admin")))
                .andExpect(status().isOk());
    }

    @Test
    void wrongCredentialsShouldNotPassAnyEndPoint() throws Exception{
        mockMvc.perform(get("/api/test/securityDBhandShakeAdmin")
                        .with(httpBasic("xxx","xxx")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void guestCanRegister() throws Exception{
        mockMvc.perform(post("/api/auth/register")
                        .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \" \",\n" +
                        "  \"lastName\": \" \",\n" +
                        "  \"email\": \" \",\n" +
                        "  \"address\": \" \",\n" +
                        "  \"phoneNumber\": \" \",\n" +
                        "  \"userName\": \" \",\n" +
                        "  \"password\": \" \"}"))
                .andExpect(status().isCreated());
    }

}
