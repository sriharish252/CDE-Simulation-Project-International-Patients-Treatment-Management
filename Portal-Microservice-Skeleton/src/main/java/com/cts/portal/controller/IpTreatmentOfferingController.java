package com.cts.portal.controller;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.feign.IPTreatmentOfferingClient;
import com.cts.portal.model.FormInputGetBySpecialistId;
import com.cts.portal.model.FormInputsGetByPackageName;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.SpecialistDetail;

@Controller
@RequestMapping("/portal")
public class IpTreatmentOfferingController {

	@Autowired
	private IPTreatmentOfferingClient client;

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
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		ModelAndView model = new ModelAndView("user-view-list-of-specialist-page");
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
		ModelAndView modelAndView = new ModelAndView("user-view-package-detail-page");
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
		ModelAndView model = new ModelAndView("user-package-detail-by-name-page");
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
	
	
	//////////////////////////////////////////////////////////////////////
	
	@GetMapping("/deleteSpecialistById")
	public ModelAndView deleteSpecialist(@ModelAttribute("formInputGetBySpecialistId") 
			FormInputGetBySpecialistId formInputGetBySpecialistId,HttpServletRequest request) throws Exception {
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			ModelAndView login = new ModelAndView("error-page401");
			return login;
		}
		
		ModelAndView model = new ModelAndView("delete-specialist-by-id");
		
		List<SpecialistDetail> specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		System.out.println("SPECIALISTS ****************");
		for (SpecialistDetail specialistDetail : specialists) {
			System.out.println(specialistDetail.getSpecialistId()+" "+specialistDetail.getName());
		}
		boolean available = false;
		for (SpecialistDetail specialistDetail : specialists) {
			if(specialistDetail.getSpecialistId() == formInputGetBySpecialistId.getSpecialistId()) {
				available = true;
				break;
			}
		}
		if(available == false && formInputGetBySpecialistId.getSpecialistId() != 0) {
				model.addObject("message", "Cannot delete, ID not available");
			return model;
		}
		
		if(formInputGetBySpecialistId.getSpecialistId()!=0)
		{ 
	 		ResponseEntity<String> entity=client.deleteSpecialist((String) request.getSession().getAttribute("Authorization"),
					formInputGetBySpecialistId.getSpecialistId());
	 		System.out.println("****************** INSIDE IF *******************");
	 		if(entity.getStatusCodeValue() == 200)
	 			model.addObject("message", "Deleted Successfully");
		}
		specialists = client
				.getAllSpecialist((String) request.getSession().getAttribute("Authorization"));
		model.addObject("specialists", specialists);
		return model;
	}
		
	//--------------------------------------------------
	@GetMapping(value = "/addSpecialist")
	public ModelAndView getAddSpecialist(	// for showing the add specialist form
			HttpServletRequest request,
			@ModelAttribute("specialistDetail") SpecialistDetail specialistDetail
			) {
		String requestToken = (String) request.getSession().getAttribute("Authorization");
		if(request.getSession().getAttribute("userName").equals("admin")) {	// checks if admin or not
			//System.out.println("\n************\n Its admin\n***************\n");
			ModelAndView mav = new ModelAndView();
			mav.addObject(specialistDetail);
			mav.setViewName("specialist-register-page");
			return mav;
		}
		else {
			return new ModelAndView("error-page401");
		}
	}
	
	
	@PostMapping(value = "/addSpecialist")
	public ModelAndView addSpecialist(		// for getting add specialist data
			HttpServletRequest request,
			@ModelAttribute("specialistDetail") SpecialistDetail specialistDetail){
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
	
	//***********************************************
	//update package get and post mapping
	@GetMapping("/updatePackage") 
	public ModelAndView viewUpdatePackage(@ModelAttribute("getPackage") GetPackage getPackage, HttpServletRequest request) throws Exception
	{ 
		if ((String) request.getSession().getAttribute("Authorization") == null) {

			ModelAndView login = new ModelAndView("error-page401");
			return login; 
		}       
		ModelAndView model = new ModelAndView("update-package"); 
		model.addObject("getPackage",getPackage);
		return model;       
	} 
	
	@PostMapping("/updatePackage") 
	public String updatePackage(@ModelAttribute("getPackage") GetPackage getPackage, Model model, HttpServletRequest request) throws Exception
	{ 
		if ((String) request.getSession().getAttribute("Authorization") == null) {
			return "error-page401"; 
		}       
		if(getPackage.getPid()!=0)
		{ 
			System.out.println("Inside update");
	 		ResponseEntity<String> entity=client.updatePackage((String) request.getSession().getAttribute("Authorization"),
	 				getPackage.getPid(), getPackage.getTreatmentPackageName());
		} 
		model.addAttribute("getPackage", getPackage);
		return "update-package";       
	} 
	
}
