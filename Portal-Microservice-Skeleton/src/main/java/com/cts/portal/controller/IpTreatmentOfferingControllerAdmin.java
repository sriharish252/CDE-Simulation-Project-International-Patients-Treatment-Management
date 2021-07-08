package com.cts.portal.controller;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cts.portal.exception.AuthorizationException;
import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.feign.AuthorisingClient;
import com.cts.portal.feign.IPTreatmentOfferingClient;
import com.cts.portal.model.FormInputsGetByPackageName;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.SpecialistDetail;

@Controller
@RequestMapping("/portal")
public class IpTreatmentOfferingControllerAdmin {	// this controller is for Admin and Admin-User

	@Autowired
	private IPTreatmentOfferingClient client;

	@Autowired
	private AuthorisingClient authClient;
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/specialists")
	public ModelAndView showSpecialistPage(HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * get the list of specialists using feign client of IPOfferingMicroservice
		 */
		System.out.println("Inside /specialists");
		List<SpecialistDetail> specialists = client.getAllSpecialist((String) request.getSession()
																					.getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("admin-view-list-of-specialist-page");
		model.addObject("specialists", specialists);
		return model;
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackages")
	public ModelAndView showIPTreatmentPackages(Model model, HttpServletRequest request) throws Exception {
		System.out.println("Inside IP Treatment Packages");
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		List<IPTreatmentPackage> packageDetails = client
				.getAllIPTreatmentPackage((String) request.getSession().getAttribute("Authorization"));
		ModelAndView modelAndView = new ModelAndView("admin-view-package-detail-page");
		modelAndView.addObject("ipTreatmentPackagekageName", packageDetails);
		return modelAndView;
	}

	/**
	 * @param formInputsGetByPackageName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ipTreatmentPackageByName")
	public ModelAndView showIPTreatmentPackageByName2(
			@ModelAttribute("formInputsGetByPackageName") FormInputsGetByPackageName formInputsGetByPackageName,
			HttpServletRequest request) throws Exception {
		
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		/*
		 * if token is set, 
		 * then allow access to view
		 */
		ModelAndView model = new ModelAndView("admin-package-detail-by-name-page");
		if (formInputsGetByPackageName != null && formInputsGetByPackageName.getAilment() != null
				&& formInputsGetByPackageName.getPackageName() != null) {
			try {
				/*
				 * get the package details by Name 
				 * using feign client of IPOfferingMicroservice
				 */
				IPTreatmentPackage ipTreatmentPackagekageName = client.getIPTreatmentPackageByName(
						formInputsGetByPackageName.getAilment(),
						formInputsGetByPackageName.getPackageName(),
						(String) request.getSession().getAttribute("Authorization"));
				model.addObject("ipTreatmentPackagekageName", ipTreatmentPackagekageName);
			} catch (IPTreatmentPackageNotFoundException e) {
				model.addObject("error", e.getMessage());
			}
		}
		return model;
	}

	@ModelAttribute("ailmentList")
	public Set<String> populateAilmentEnumList() {
		return EnumSet.allOf(com.cts.portal.model.AilmentCategory.class).stream().map(a -> a.name())
				.collect(Collectors.toSet());

	}

	@ModelAttribute("packageList")
	public List<String> populatePackageList() {
		return Arrays.asList("Package 1", "Package 2");

	}
	
	// my work - Deep Roy
	
	
	@GetMapping(value = "/addSpecialist")
	public ModelAndView getAddSpecialist(	// for showing the add specialist form
			HttpServletRequest request,
			@ModelAttribute("specialistDetail") SpecialistDetail specialistDetail
			) {
		String requestToken = (String) request.getSession().getAttribute("Authorization");
		if(authClient.authorizeTheRequestIfAdmin(requestToken)) {	// checks if admin or not
			//System.out.println("\n************\n Its admin\n***************\n");
			ModelAndView mav = new ModelAndView();
			mav.addObject(specialistDetail);
			mav.setViewName("admin-specialist-register-page");
			return mav;
		}
		else {
			return new ModelAndView("error-page401");
		}
	}
	
	
	@PostMapping(value = "/addSpecialist")
	public ModelAndView addSpecialist(		// for getting add specialist data
			HttpServletRequest request,
			@ModelAttribute("specialistDetail") SpecialistDetail specialistDetail) {
		//System.out.println(specialistDetail);
		String requestToken = (String) request.getSession().getAttribute("Authorization");
		if(request.getSession().getAttribute("userName").equals("admin")) {	// checks if admin or not
			//System.out.println("\n************\n Its admin\n***************\n");
			if (requestToken != null) {
				// adding the specialist using FeignClient
				ResponseEntity<String> responseFromClient = client.addSpecialist(requestToken, specialistDetail);
				// creating ModelAndView
				ModelAndView mav = new ModelAndView();
				mav.addObject("message", "New Specialist Added!");
				try {
					mav.addObject("specialists", client.getAllSpecialist(requestToken));
				} catch (AuthorizationException e) {
				System.out.println("Can't fetch Specialist List");
				}
				mav.setViewName("admin-view-list-of-specialist-page");
				mav.setStatus(responseFromClient.getStatusCode());	// taking HttpStatus from FeignClient response
				return mav;
			}
			else {
				ModelAndView login = new ModelAndView("error-page401");
				login.setStatus(HttpStatus.UNAUTHORIZED);
				return login;	
			}
		}
		else {
			//System.out.println("\n************\n Its Not admin\n***************\n");
			ModelAndView login = new ModelAndView("error-page401");
			login.setStatus(HttpStatus.UNAUTHORIZED);
			return login;	
		} 
	}
	
	
	
	
	
	
	
	
}
