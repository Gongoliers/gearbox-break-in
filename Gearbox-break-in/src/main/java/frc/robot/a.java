package frc.robot;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSFunctionCallback;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

// STACK OVERFLOW: https://stackoverflow.com/questions/7176981/how-to-use-html-and-css-as-a-java-application-gui

// ANGULAR JS EXAMPLE https://medium.com/bb-tutorials-and-thoughts/how-to-develop-and-build-angular-app-with-java-backend-87fb603c6e17

public class a {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    JSObject window = (JSObject)
                            browser.executeJavaScriptAndReturnValue("window");
                    window.setProperty("MyFunction", new JSFunctionCallback() {
                        @Override
                        public Object invoke(Object... args) {
                            for (Object arg : args) {
                                System.out.println("arg = " + arg);
                            }
                            return "Hello!";
                        }
                    });
                    JSValue returnValue = browser.executeJavaScriptAndReturnValue(
                            "MyFunction('Hello JxBrowser!', 1, 2, 3, true);");
                    System.out.println("return value = " + returnValue);
                }
            }
        });
        browser.loadURL("about:blank");
    }
}