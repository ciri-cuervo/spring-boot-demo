package com.springdemo.web;

/**
 * Class with constants for controllers.
 */
public interface WebConstants {

	String REDIRECT = "redirect:";
	String FORWARD = "forward:";

	/**
	 * Class with redirect constants
	 */
	interface Redirect {
		String RedirectToHome = REDIRECT + "/";
		String RedirectToInfoMsg = REDIRECT + "/infomsg";
		String RedirectToErrorMsg = REDIRECT + "/errormsg";
	}

	/**
	 * Class with view name constants
	 */
	interface Views {

		interface Pages {

			interface Account {
				String AccountHomePage = "pages/account/accountHomePage";
				String AccountLoginPage = "pages/account/accountLoginPage";
				String AccountSignupPage = "pages/account/accountSignupPage";
				String AccountForgotPasswordPage = "pages/account/accountForgotPasswordPage";
				String AccountChangePasswordPage = "pages/account/accountChangePasswordPage";
				String AccountProfilePage = "pages/account/accountProfilePage";
				String AccountProfileEditPage = "pages/account/accountProfileEditPage";
				String AccountProfileEmailEditPage = "pages/account/accountProfileEmailEditPage";
			}

			interface Common {
				String CommonInfoPage = "pages/common/commonInfoPage";
				String CommonErrorPage = "pages/common/commonErrorPage";
			}

			interface Error {
				String Error40xPage = "pages/error/error40xPage";
				String Error50xPage = "pages/error/error50xPage";
			}

			interface Misc {
				String MiscHomePage = "pages/misc/miscHomePage";
				String MiscRobotsPage = "pages/misc/miscRobotsPage";
			}
		}

		interface Emails {

			interface Account {
				String AccountActivationEmail = "emails/account/accountActivationEmail";
				String AccountChangePasswordEmail = "emails/account/accountChangePasswordEmail";
			}
		}
	}
}
