package com.carbontest.treasuremap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.carbontest.treasuremap.service.ITreasureService;

@Controller
public class TreasureController {
	
	ITreasureService treasureService;
	
	public ITreasureService getTreasureService() {
		return treasureService;
	}

	public TreasureController(@Autowired ITreasureService treasureService) {
		this.treasureService=treasureService;
		
	}
	
	@GetMapping("/")
	public ModelAndView displayHome() {
	    ModelAndView modelAndView = new ModelAndView("index.html");
	    this.getTreasureService().executeGame();
	    return modelAndView;
	}

}
