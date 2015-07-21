package com.springdemo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springdemo.domain.EmailHash;
import com.springdemo.domain.types.EmailType;
import com.springdemo.service.RegistrationService;
import com.springdemo.util.WebUtils;
import com.springdemo.web.command.ChangePasswordCommand;
import com.springdemo.web.command.ChangePasswordCommandValidator;
import com.springdemo.web.command.ForgotPasswordCommand;
import com.springdemo.web.command.ForgotPasswordCommandValidator;
import com.springdemo.web.command.HashCommand;
import com.springdemo.web.command.HashCommandValidator;
import com.springdemo.web.command.SignupCommand;
import com.springdemo.web.command.SignupCommandValidator;
import com.springdemo.web.tags.BreadcrumbTag;
import com.springdemo.web.tags.MetaTag;

@Controller
@RequestMapping("/account")
public class AccountPageController {

	@Autowired
	public AuthenticationManager authenticationManager;
	@Autowired
	protected RegistrationService registrationService;

	/*
	 * Validators
	 */

	@Autowired
	private SignupCommandValidator signupCommandValidator;
	@Autowired
	private ForgotPasswordCommandValidator forgotPasswordCommandValidator;
	@Autowired
	private ChangePasswordCommandValidator changePasswordCommandValidator;
	@Autowired
	private HashCommandValidator hashCommandValidator;

	/*
	 * Bind validators
	 */

	@InitBinder("signupCommand")
	protected void initSignupBinder(WebDataBinder binder)
	{
		binder.addValidators(signupCommandValidator);
	}

	@InitBinder("forgotPasswordCommand")
	protected void initForgotPasswordBinder(WebDataBinder binder)
	{
		binder.addValidators(forgotPasswordCommandValidator);
	}

	@InitBinder("changePasswordCommand")
	protected void initChangePasswordBinder(WebDataBinder binder)
	{
		binder.addValidators(changePasswordCommandValidator);
	}

	@InitBinder("hashCommand")
	protected void initHashBinder(WebDataBinder binder)
	{
		binder.addValidators(hashCommandValidator);
	}

	/*
	 * Model attributes
	 */

	@ModelAttribute("regexUsernamePattern")
	public String getRegexUsernamePattern()
	{
		return WebUtils.REGEX_USERNAME_PATTERN;
	}

	@ModelAttribute("regexEmailPattern")
	public String getRegexEmailPattern()
	{
		return WebUtils.REGEX_EMAIL_PATTERN;
	}

	@ModelAttribute("regexPasswordPattern")
	public String getRegexPasswordPattern()
	{
		return WebUtils.REGEX_PASSWORD_PATTERN;
	}

	@ModelAttribute("regexHashPattern")
	public String getRegexHashPattern()
	{
		return WebUtils.REGEX_HASH_PATTERN;
	}

	/*
	 * Set up model
	 */

	protected void setUpPageInformation(Model model, String title, String header)
	{
		model.addAttribute("title", title);
		model.addAttribute("header", header);

		List<BreadcrumbTag> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new BreadcrumbTag("account.header", WebUtils.isAuthenticated() ? "/account"
				: null));
		breadcrumbs.add(new BreadcrumbTag(header));
		model.addAttribute("breadcrumbs", breadcrumbs);

		List<MetaTag> metatags = new ArrayList<>();
		metatags.add(new MetaTag("robots", "none"));
		model.addAttribute("metatags", metatags);
	}

	protected void setUpActiveNavItem(Model model, String activeItem)
	{
		model.addAttribute("nav_active_" + activeItem, "active");
	}

	/**
	 * Login
	 */

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request)
	{
		return WebConstants.Views.Pages.Account.AccountLoginPage;
	}

	/**
	 * Sign Up
	 */

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm(Model model)
	{
		model.addAttribute(new SignupCommand());
		setUpPageInformation(model, "account.signup.title", "account.signup.header");
		setUpActiveNavItem(model, "signup");
		return WebConstants.Views.Pages.Account.AccountSignupPage;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String submitSignup(@Valid @ModelAttribute SignupCommand signupCommand,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs)
	{

		setUpPageInformation(model, "account.signup.title", "account.signup.header");
		setUpActiveNavItem(model, "signup");

		if (bindingResult.hasErrors())
		{
			return WebConstants.Views.Pages.Account.AccountSignupPage;
		}

		@SuppressWarnings("unused")
		EmailHash emailHash = registrationService.createUser(signupCommand);

		// try {
		// mailSenderService.sendHashMail(emailHash, "account.activation.email.subject",
		// WebConstants.Views.Emails.Account.AccountActivationEmail);
		// } catch (MessagingException e) {
		// redirectAttrs.addFlashAttribute("errorMsg", "account.signup.error");
		// return WebConstants.Redirect.RedirectToErrorMsg;
		// }

		redirectAttrs.addFlashAttribute("infoMsg", "account.signup.info");
		return WebConstants.Redirect.RedirectToInfoMsg;
	}

	/**
	 * Activate user
	 */

	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	public String activation(@Valid HashCommand hashCommand, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttrs)
	{

		if (bindingResult.hasErrors())
		{
			redirectAttrs.addFlashAttribute("errorMsg", "account.activation.error");
			redirectAttrs.addFlashAttribute("errors", bindingResult.getGlobalErrors());
			return WebConstants.Redirect.RedirectToErrorMsg;
		}

		// TODO log out current user
		registrationService.activateUser(hashCommand);

		redirectAttrs.addFlashAttribute("infoMsg", "account.activation.info");
		return WebConstants.Redirect.RedirectToInfoMsg;
	}

	/**
	 * Reset Password
	 */

	@RequestMapping(value = "/password/reset", method = RequestMethod.GET)
	public String forgotPasswordForm(Model model)
	{
		model.addAttribute(new ForgotPasswordCommand());
		return WebConstants.Views.Pages.Account.AccountForgotPasswordPage;
	}

	@RequestMapping(value = "/password/reset", method = RequestMethod.POST)
	public String submitForgotPassword(
			@Valid @ModelAttribute ForgotPasswordCommand forgotPasswordCommand,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs)
	{

		if (bindingResult.hasErrors())
		{
			forgotPasswordCommand.setCode("");
			return WebConstants.Views.Pages.Account.AccountForgotPasswordPage;
		}

		@SuppressWarnings("unused")
		EmailHash emailHash = registrationService.createEmailHash(forgotPasswordCommand.getEmail(),
				EmailType.RESTORE);

		// try {
		// mailSenderService.sendHashMail(emailHash, "account.changepassword.email.subject",
		// WebConstants.Views.Emails.Account.AccountChangePasswordEmail);
		// } catch (MessagingException e) {
		// redirectAttrs.addFlashAttribute("errorMsg", "account.forgotpassword.error");
		// return WebConstants.Redirect.RedirectToErrorMsg;
		// }

		redirectAttrs.addFlashAttribute("infoMsg", "account.forgotpassword.info");
		return WebConstants.Redirect.RedirectToInfoMsg;
	}

	@RequestMapping(value = "/password/reset/confirm", method = RequestMethod.GET)
	public String changePasswordForm(@Valid HashCommand hashCommand, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttrs)
	{

		if (bindingResult.hasErrors())
		{
			redirectAttrs.addFlashAttribute("errorMsg", "account.changepassword.error");
			redirectAttrs.addFlashAttribute("errors", bindingResult.getGlobalErrors());
			return WebConstants.Redirect.RedirectToErrorMsg;
		}

		// TODO log out current user
		model.addAttribute(new ChangePasswordCommand());
		return WebConstants.Views.Pages.Account.AccountChangePasswordPage;
	}

	@RequestMapping(value = "/password/reset/confirm", method = RequestMethod.POST)
	public String submitChangePassword(
			@Valid @ModelAttribute ChangePasswordCommand changePasswordCommand,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs)
	{

		if (bindingResult.hasFieldErrors())
		{
			return WebConstants.Views.Pages.Account.AccountChangePasswordPage;
		}

		if (bindingResult.hasErrors())
		{
			redirectAttrs.addFlashAttribute("errorMsg", "account.changepassword.error");
			redirectAttrs.addFlashAttribute("errors", bindingResult.getGlobalErrors());
			return WebConstants.Redirect.RedirectToErrorMsg;
		}

		registrationService.changePassword(changePasswordCommand);

		redirectAttrs.addFlashAttribute("infoMsg", "account.changepassword.info");
		return WebConstants.Redirect.RedirectToInfoMsg;
	}

}
