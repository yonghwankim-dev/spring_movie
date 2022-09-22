package kr.yh.movie.vo;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "pageList")
@Log
public class PageMarker<T> {
    private Page<T> result;

    private Pageable prevPage;      // prevPage 페이지 목록의 맨 앞인 '이전'으로 이동하는데 필요한 정보를 가진 Pageable
    private Pageable currentPage;   // 현재 페이지의 정보를 가진 Pageable
    private Pageable nextPage;      // 페이지 목록 맨 뒤의 '다음'으로 이동하는데 필요한 정보를 가진 Pageable

    private int currentPageNum;     // 화면에 보이는 1부터 시작하는 페이지 번호
    private int totalPageNum;       // 전페 페이지 번호

    private List<Pageable> pageList; // 페이지 번호의 시작부터 끝까지의 Pageable들을 저장한 리스트

    public PageMarker(Page<T> result) {
        this.result         = result;
        this.currentPage    = result.getPageable();
        this.currentPageNum = currentPage.getPageNumber() + 1;
        this.totalPageNum   = result.getTotalPages();
        this.pageList       = new ArrayList<>();
        calcPages();
    }

    private void calcPages(){
        // ex) currentPageNum = 17 => tempEndNum = 20
        int tempEndNum = (int) (Math.ceil(this.currentPageNum / 10.0) * 10); // 현재 페이지의 끝 번호
        int startNum   = tempEndNum - 9; // 현재 페이지 시작 번호

        // 현제 페이지를 시작 페이지로 이동
        Pageable startPage = this.currentPage;
        for(int i = startNum; i < this.currentPageNum; i++){
            startPage = startPage.previousOrFirst();
        }
        // 현재 페이지의 번호가 0이하라면 맨 처음 페이지이므로 이전 페이지는 null로 저장
        this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();

        if(this.totalPageNum < tempEndNum){
            tempEndNum = this.totalPageNum;
            this.nextPage = null;
        }

        // 한 단위 페이지들을 리스트에 저장
        for(int i = startNum; i <= tempEndNum; i++){
            pageList.add(startPage);
            startPage = startPage.next();
        }
        this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
    }
}
