package com.bethefirst.lifeweb.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Character.getNumericValue;
import static java.lang.String.valueOf;

@Component
@Slf4j
@NoArgsConstructor
public class UrlUtil {

    /** SNS URL 검사 */
    public void inspectionUrl(String url) {

        try {
            URL urlInstance = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlInstance.openConnection();
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode " + responseCode);

            //char 타입을 int 로 변환
            int statusCode = getNumericValue(valueOf(responseCode).charAt(0));
            if(statusCode == 4 || statusCode == 5){
                throw new IllegalArgumentException("존재하지 않는 URL 입니다 다시 확인해주세요. " + url);
            }
        }catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL 형식이 잘못 되었습니다. " + url);
        } catch (IOException e) {
            throw new RuntimeException("URL 검사에 실패 하였습니다.");
        }

    }

}
