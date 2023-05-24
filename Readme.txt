
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Qmobile-IT:NoqoodyPaySDK:version'
	}


For Payment:

            NoqoodyPay.Pay(Activity activity, String baseURL, String UserName, String Password, Double Amount,
                    String CustomerEmail, String CustomerMobile, String ProjectCode, String Description,
                    String RedirectURL, String Reference, String ClientSecret);


     public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == NoqoodyPay_Keys.Activity_RequestCode) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getBooleanExtra(NoqoodyPay_Keys.paymentresult_status, false)) {

                        //Toast.makeText(this, data.getStringExtra(NoqoodyPay_Keys.paymentresult), Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(this, data.getStringExtra(NoqoodyPay_Keys.paymentresult), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }