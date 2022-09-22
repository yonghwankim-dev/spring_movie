package kr.yh.movie.util;

import kr.yh.movie.vo.PageVO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class RedirectAttributeUtil {
    public static void addAttributesPage(PageVO pageVO, RedirectAttributes rttr){
        rttr.addAttribute("page", pageVO.getPage());
        rttr.addAttribute("size", pageVO.getSize());
        rttr.addAttribute("type", pageVO.getType());
        rttr.addAttribute("keyword", pageVO.getKeyword());
    }
}
