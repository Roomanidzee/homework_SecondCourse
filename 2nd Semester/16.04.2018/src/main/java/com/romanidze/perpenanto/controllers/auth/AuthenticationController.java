package com.romanidze.perpenanto.controllers.auth;

import com.romanidze.perpenanto.domain.user.FileInfo;
import com.romanidze.perpenanto.domain.user.User;
import com.romanidze.perpenanto.forms.user.AddProductForm;
import com.romanidze.perpenanto.security.role.Role;
import com.romanidze.perpenanto.services.interfaces.auth.AuthenticationService;
import com.romanidze.perpenanto.services.interfaces.files.FileStorageService;
import com.romanidze.perpenanto.services.interfaces.files.PhotoService;
import com.romanidze.perpenanto.services.interfaces.user.ProfileService;
import com.romanidze.perpenanto.services.interfaces.user.UserService;
import com.romanidze.perpenanto.utils.FileStorageUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 15.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Controller("Authentication Controller Annotation")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private PhotoService photoService;
    private FileStorageService fileStorageService;
    private UserService userService;
    private ProfileService profileService;


    @GetMapping("/login")
    public String login(@ModelAttribute("model") ModelMap model, Authentication authentication,
                        @RequestParam Optional<String> error) {
        if (authentication != null) {
            return "redirect:/";
        }
        model.addAttribute("error", error);
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Authentication authentication) {
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null) {
            User user = this.authenticationService.getUserByAuthentication(authentication);
            if (user.getRole().equals(Role.USER)) {
                return "redirect:/user/profile";
            }else if(user.getRole().equals(Role.ADMIN)){
                return "redirect:/admin/index";
            }
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/user/profile")
    public ModelAndView getProfilePage(Authentication authentication){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        ModelAndView modelAndView = new ModelAndView();

        Optional<FileInfo> profilePhoto = this.photoService.getProfilePhoto(user.getProfile());

        profilePhoto.ifPresent(fileInfo -> modelAndView.addObject("photo", fileInfo.getStorageFileName()));

        modelAndView.addObject("reservations", this.profileService.getReservationInformation(user));
        modelAndView.addObject("products", this.profileService.getProductsByUser(user));
        modelAndView.addObject("reservationCount", this.profileService.countReservations(user));
        modelAndView.addObject("commonProductsPrice", this.profileService.getCommonProductsPrice(user));
        modelAndView.addObject("spendedMoneyOnReservations",
                                  this.profileService.getSpendedMoneyOnReservations(user));
        modelAndView.addObject("soldedProductsCount", this.profileService.getSoldedProductsCount(user));
        modelAndView.addObject("id", user.getId());
        modelAndView.addObject("email", user.getProfile().getEmail());
        modelAndView.addObject("username", user.getLogin());
        modelAndView.addObject("name", user.getProfile().getPersonName());
        modelAndView.addObject("surname", user.getProfile().getPersonSurname());
        modelAndView.addObject("addresses", user.getAddresses());

        modelAndView.setViewName("auth/profile");
        return modelAndView;

    }

    @PostMapping("/user/send_photo")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, Authentication authentication){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        boolean sent = this.fileStorageService.saveFile(file, user.getProfile());

        if (sent) {
            return new ModelAndView("auth/send_photo");
        }
        throw new NullPointerException("Photo not sended!");

    }

    @PostMapping(value = "/user/profile", params = {"product_action"})
    public ModelAndView addProduct(@ModelAttribute("productForm") AddProductForm productForm,
                                   Authentication authentication){

        User user = this.authenticationService.getUserByAuthentication(authentication);
        this.userService.addProduct(user, productForm);
        return new ModelAndView("auth/add_product");

    }

    @GetMapping("/get_photo/{file-name:.+}")
    public void getPhoto(@PathVariable("file-name") String fileName, HttpServletResponse response){

        this.fileStorageService.writeFileToResponse(fileName, response);

    }

    @GetMapping("/admin/index")
    public ModelAndView getIndexPage(){
        return new ModelAndView("admin/admin_page");
    }

}
