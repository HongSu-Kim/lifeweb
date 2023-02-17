package com.bethefirst.lifeweb.service.review;

import com.bethefirst.lifeweb.dto.review.reqeust.CreateReviewDto;
import com.bethefirst.lifeweb.dto.review.reqeust.ReviewSearchRequirements;
import com.bethefirst.lifeweb.dto.review.reqeust.UpdateReviewDto;
import com.bethefirst.lifeweb.dto.review.response.ReviewDto;
import com.bethefirst.lifeweb.entity.application.Applicant;
import com.bethefirst.lifeweb.entity.application.ApplicantStatus;
import com.bethefirst.lifeweb.entity.campaign.Campaign;
import com.bethefirst.lifeweb.entity.campaign.CampaignStatus;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.review.Review;
import com.bethefirst.lifeweb.repository.application.ApplicantRepository;
import com.bethefirst.lifeweb.repository.campaign.CampaignRepository;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.repository.review.ReviewRepository;
import com.bethefirst.lifeweb.service.review.interfaces.ReviewService;
import com.bethefirst.lifeweb.util.SeleniumUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.Duration.ofSeconds;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final CampaignRepository campaignRepository;
	private final ApplicantRepository applicantRepository;
	private final SeleniumUtil seleniumUtil;
	private static final String TITLE = "title";
	private static final String IMG = "imgSrc";




	/** 리뷰 등록 */
	@Override
	public void createReview(CreateReviewDto createReviewDto, Long memberId) {

		Member member = memberRepository.findById(memberId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 회원 입니다. " + memberId));

		Campaign campaign = campaignRepository.findById(createReviewDto.getCampaignId()).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 캠페인 입니다 " + createReviewDto.getCampaignId()));

		Applicant applicant = applicantRepository.findByMemberIdAndApplicationCampaignId(memberId, createReviewDto.getCampaignId()).orElseThrow(() ->
				new IllegalArgumentException("해당 캠페인에 신청서가 존재하지 않습니다. "));

		//선정된 캠페인인 맞는지 확인합니다.
		if(applicant.getStatus() != ApplicantStatus.SELECT){
			throw new IllegalArgumentException("선정되지 않은 캠페인입니다.");
		}

		//해당 캠페인이 리뷰 등록기간인지 확인합니다.
		if(campaign.getStatus() != CampaignStatus.FILING){
			throw new IllegalArgumentException("리뷰 등록 기간이 아닙니다.");
		}

		//URL 로 리뷰의 제목과 이미지를 크롤링 해옵니다.
		Map<String, String> crawlingReviewData = reviewCrawling(createReviewDto.getReviewUrl());

		Review review = createReviewDto.createReview(member, campaign , crawlingReviewData);

		reviewRepository.save(review);
	}

	/** 리뷰 삭제 */
	@Override
	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 리뷰입니다. " + reviewId));

		reviewRepository.delete(review);

	}

	/** 리뷰 수정 */
	@Override
	public void updateReview(UpdateReviewDto updateReviewDto, Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 리뷰입니다. " + reviewId));

		//URL 로 리뷰의 제목과 이미지를 크롤링 해옵니다.

		updateReviewDto.updateReview(review);
	}

	/** 검색조건에 따른 리뷰 전체 조회 */
	@Transactional(readOnly = true)
	@Override
	public Page<ReviewDto> getReviewList(ReviewSearchRequirements requirements, Pageable pageable) {
		return reviewRepository.findAllBySearchRequirements(requirements, pageable).map(ReviewDto::new);
	}

	/** 리뷰를 URL (인스타/네이버/유투브) 분류해서 크롤링작업을 합니다. */
	private Map<String, String> reviewCrawling(String url) {

		if(url.contains("naver.com")){
			return crawlingByNaver(url);
		}else if(url.contains("instagram.com")){
			return crawlingByInstagram(url);
		}else if(url.contains("youtube.com")){
			return crawlingByYoutube(url);
		}

		return null;
	}

	/** 크롬으로 유투브에서 리뷰의 제목과 썸네일을 가져옵니다. */
	private Map<String, String> crawlingByYoutube(String url) {

		String titleXpath = "//*[@id=\"title\"]/h1/yt-formatted-string";
		String nameHrefPath = "//*[@id=\"text\"]/a";



		String imgSrc = null;
		Map<String,String> crawlingReviewData = new HashMap<>();

		//크롬드라이버를 받아옵니다.
		ChromeDriver driver = seleniumUtil.getDriver();
		//드라이버가 뜰떄까지 최대 30초정도 대기합니다.
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(30));

		driver.get(url);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(titleXpath)));

		//동영상 제목을 받아옵니다.
		String title = driver.findElement(By.xpath(titleXpath)).getText();


		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(nameHrefPath)));
		//동영상 페이지로 이동할 herf를 받아옵니다
		String href = driver.findElement(By.xpath(nameHrefPath)).getAttribute("href");
		String videosUrl = href + "/videos";
		//동영상 페이지로 이동 합니다.
		driver.get(videosUrl);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#dismissible")));
		List<WebElement> elements = driver.findElements(By.cssSelector("#dismissible"));

		//페이지를 맨 끝까지 내립니다.
		new Actions(driver).sendKeys(Keys.END).perform();
		new Actions(driver).sendKeys(Keys.END).perform();

		for (WebElement element : elements) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#video-title")));

			//동영상 제목을 읽어옵니다.
			String videoTitle = element.findElement(By.cssSelector("#video-title")).getText();


			//동영상 제목과 같은 썸네일이 있다면 썸네일을 읽어옵니다.
			if(videoTitle.equals(title)){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#thumbnail > yt-image > img")));
				imgSrc = element.findElement(By.cssSelector("#thumbnail > yt-image > img")).getAttribute("src");
				break;
			}

		}

		crawlingReviewData.put(TITLE, title);
		crawlingReviewData.put(IMG, imgSrc);
		return crawlingReviewData;
	}


	/** 크롬으로 인스타에서 리뷰의 제목과 썸네일을 가져옵니다. */
	private Map<String, String> crawlingByInstagram(String url) {

		String imgSrc = null;
		String content = null;
		Map<String,String> crawlingReviewData = new HashMap<>();

		//크롬드라이버를 받아옵니다.
		ChromeDriver driver = seleniumUtil.getDriver();
		//드라이버가 뜰떄까지 최대 30초정도 대기합니다.
		WebDriverWait wait = new WebDriverWait(driver, ofSeconds(30));
		driver.get(url);




		//해당 이미지 태그가 뜰떄까지 기다립니다.
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='_aagu _aato'] img")));

		imgSrc = driver.findElement(By.cssSelector("div[class='_aagu _aato'] img")).getAttribute("src");
		content = driver.findElement(By.cssSelector("div[class='_a9zs']")).getText();

		//가져온 콘텐트의 엔터(개행문자)를 짤라서 띄어쓰기로 정리합니다.
		String contentBySplit = createContentBySplit(content);

		log.info("content ={}",contentBySplit);
		log.info("imgSrc  {}", imgSrc);

		crawlingReviewData.put(TITLE, contentBySplit);
		crawlingReviewData.put(IMG, imgSrc);
		return crawlingReviewData;

	}

	/** 인스타에서 가져온 콘텐트의 엔터(개행문자)를 짤라서 띄어쓰기로 정리합니다. */
	private static String createContentBySplit(String content) {
		String[] split = content.split("\\n");
		StringBuilder sb = new StringBuilder();
		for (String s : split) {
			sb.append(s + " " );
		}

		if(sb.length() > 80){
			return content = sb.substring(0, 80);
		}

		return content = sb.toString();

	}
	/** 네이버에서 리뷰의 제목과 썸네일을 가져옵니다. */
	private Map<String, String> crawlingByNaver(String url) {

		Document doc = null; // 아이프레임
		Document blogDocument = null; //아이프레임에서 가져온 실제 블로그 document
		String title = null; //크롤링 해온 블로그제목
		String imgSrc = null; //크롤링 해온 이미지주소
		Map<String,String> crawlingReviewData = new HashMap<>();
		try {

			//아이프레임 connection
			doc = Jsoup.connect(url).get();
			Elements iframes = doc.select("iframe#mainFrame");
			String src = iframes.attr("src");

			//진짜 블로그 주소 document 가져오기
			String blogUrl = "http://blog.naver.com"+ src;
			blogDocument = Jsoup.connect(blogUrl).get();

			//블로그에서 네비게이트및 사이드바를 제외한 본문 가져오기
			Element element = blogDocument.getElementById("postListBody");

			title = element.select(".se-title-text").text(); //제목
			imgSrc = element.select(".se-image-resource").attr("src"); //이미지


		} catch (IOException ie) {
			log.error("크롤링을 처리하던 중 IOException 발생 ", ie);
		}catch (NullPointerException ne){
			log.error("크롤링을 처리하던 중 css 발견하지 못했습니다. hint:(네이버 로직이 변경 됐을 수 있습니다 ", ne);
		}

		crawlingReviewData.put(TITLE, title);
		crawlingReviewData.put(IMG, imgSrc);
		return crawlingReviewData;

	}

}
