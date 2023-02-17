package com.bethefirst.lifeweb.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SeleniumUtil {



    public ChromeDriver getDriver(){

        //WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", "/Users/DONGWOO/study/chromedriver");

        //WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");       //팝업안띄움
//		options.addArguments("--start-maximized"); 				//전체화면으로 실행
        options.addArguments("--headless","-no-sandbox"); //브라우저 안띄움
		options.addArguments("window-size=1920x1080");
//		인스타에서 user 확인하는 작업때문에 넣는다고 함
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        options.addArguments("lang=ko_KR");
        options.addArguments("--disable-gpu");			//gpu 비활성화
        options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
        options.addArguments("--disable-default-apps"); // 기본앱 사용안함

        ChromeDriver driver = new ChromeDriver(options);

        return driver;

    }

}
