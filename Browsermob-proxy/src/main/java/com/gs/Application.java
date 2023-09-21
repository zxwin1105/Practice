package com.gs;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.*;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * @author zxwin
 * @date 2022/12/30
 */
public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","C:/Users/zxwin/Downloads/chromedriver_win32/chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://www.baidu.com");
//        // 3.获取输入框，输入selenium
//        driver.findElement(By.id("kw")).sendKeys("selenium");
//        // 4.获取“百度一下”按钮，进行搜索
//        driver.findElement(By.id("su")).click();
//        // 5.退出浏览器
//        driver.quit();
//        System.setProperty("webdriver.chrome.driver", webDriverDir);
        BrowserMobProxy browserMobProxy = new BrowserMobProxyServer();
        browserMobProxy.start();
        browserMobProxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        browserMobProxy.setHarCaptureTypes(CaptureType.RESPONSE_CONTENT);
        browserMobProxy.newHar("kk");

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(browserMobProxy);
        // 设置浏览器参数
        ChromeOptions options = new ChromeOptions();
        options.setProxy(seleniumProxy);
        options.setAcceptInsecureCerts(true);
        options.setExperimentalOption("useAutomationExtension", false);
        // 创建驱动对象
        WebDriver driver = new ChromeDriver(options);

        // 监听网络请求
        browserMobProxy.addRequestFilter((request, contents, messageInfo) -> {
            // 打印浏览器请求的url和请求头
            System.out.println(request.getUri() + " --->> " + request.headers().get("Cookie"));
            return null;
        });

        // 打开链接
        driver.get("https://www.baidu.com/");

        // 获取返回的请求内容
        Har har = browserMobProxy.getHar();
        List<HarEntry> entries = har.getLog().getEntries();
        for (HarEntry harEntry : entries) {
            HarResponse response = harEntry.getResponse();
            HarRequest request = harEntry.getRequest();
            String url = harEntry.getRequest().getUrl();
            List<HarNameValuePair> headers = request.getHeaders();
            for (HarNameValuePair harp : headers) {
                System.out.println(harp.toString());
            }
        }

    }
}
