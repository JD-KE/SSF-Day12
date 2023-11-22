package sg.edu.nus.ssf.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.ssf.workshop12.exception.RandNoException;
import sg.edu.nus.ssf.workshop12.model.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenerateRandController {

    @GetMapping(path="/show")
    public String showRandomForm(Model m) {
        Generate g = new Generate();
        m.addAttribute("generateObj", g);
        return "generate";
    }

    @GetMapping(path="/generate")
    public String generate(@RequestParam Integer numberVal, Model m) {

        this.randomizeNumber(m, numberVal.intValue());
        return "result";
    }

    private void randomizeNumber (Model m, int noOfGenerateNo) {
        int maxGenNo = 31;
        // validate noOfGenerateNo cannot be less than 1 and more than maxGenNo
        String[] imgNumbers = new String[maxGenNo];
        if (noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo - 1) {
            throw new RandNoException();
        }

        // Gen number images file name and set to the array
        for (int i = 0; i < maxGenNo; i++) {
            if (i > 0) {
                imgNumbers[i] = String.format("number%d.jpg", i);
                imgNumbers[i] = "number" + i + ".jpg";
            }
        }

        List<String> selectedImgs = new ArrayList<>();
        Random rand = new Random();
        
        Set<Integer> uniqueResult = new LinkedHashSet<>();
        while(uniqueResult.size() < noOfGenerateNo){
            Integer randNoResult = rand.nextInt(maxGenNo);
            if (randNoResult != null) {
                if (randNoResult > 0) {
                    uniqueResult.add(randNoResult);
                }
            }
        }

        Integer currElem = null;
        for (Iterator iter = uniqueResult.iterator(); iter.hasNext();) {
            currElem = (Integer)iter.next();
            // System.out.println(currElem);
            if(currElem != null) {
                selectedImgs.add(imgNumbers[currElem]);
                // System.out.println(imgNumbers[currElem]);
            }
        }

        m.addAttribute("noOfGenerateNo", noOfGenerateNo);
        m.addAttribute("selectedImgs", selectedImgs);

    }
    
}
