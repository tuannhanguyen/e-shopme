package com.shopme.admin.setting;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Currency;
import com.shopme.common.entity.Setting;

@Controller
public class SettingController {

	@Autowired private SettingService settingService;
	@Autowired private CurrencyRepository currencyRepository;
	
	@GetMapping("/settings")
	public String listAll(Model model) {
		
		List<Setting> listSettings = settingService.listAllSettings();
		List<Currency> listCurrencies = currencyRepository.findAllByOrderByNameAsc();
		
		for(Setting setting : listSettings) {
			model.addAttribute(setting.getKey(), setting.getValue());
		}
		
		model.addAttribute("listCurrencies", listCurrencies);
		return "settings/settings";
	}
	
	@PostMapping("/setting/save_general")
	public String saveGeneralSettings(@RequestParam("fileImage") MultipartFile multipartFile,
			HttpServletRequest request, RedirectAttributes ra) throws IOException {
		
		GeneralSettingBag settingBag = settingService.getGeneralSetting();
		saveSiteLogo(multipartFile, settingBag);
		saveCurrencySymbol(request, settingBag);
		
		updateSettingValuesFromForm(request, settingBag.list());
		
		ra.addFlashAttribute("message", "General settings have been saved.");
		return "redirect:/settings";
	}

	private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String value = "/site-logo/" + fileName;
			settingBag.updateSiteLogo(value);
			
			String uploadDir = "../site-logo/";
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
	}
	
	private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag settingBag) {
		Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
		Optional<Currency> findByIdResult = currencyRepository.findById(currencyId);
		
		if(findByIdResult.isPresent()) {
			Currency currency = findByIdResult.get();
			String symbol = currency.getSymbol();
			settingBag.updateCurrencySymbol(symbol);
		}
	}
	
	//doc gia tri trong form & update
	private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
		for(Setting setting : listSettings) {
			String value = request.getParameter(setting.getKey());
			System.out.println("KQ: " + setting.getKey() + "-" + value);
			if(value != null) {
				setting.setValue(value);
			}
		}
		
		settingService.saveAll(listSettings);
	}
	
	@PostMapping("/setting/save_mail_server")
	public String saveSettingMailServer(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		List<Setting> listMailServerSetting = settingService.getMailServerSetting();
		updateSettingValuesFromForm(request, listMailServerSetting);
		
		redirectAttributes.addFlashAttribute("message", "Mail server settings have been saved.");
		
		return "redirect:/settings";
	}
	
	@PostMapping("/setting/save_mail_templates")
	public String saveMailTemplatesSetting(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		List<Setting> listMailTemplatesSetting = settingService.getMailTemplatesSetting();
		updateSettingValuesFromForm(request, listMailTemplatesSetting);
		
		redirectAttributes.addFlashAttribute("message", "Mail template settings have been saved.");
		
		return "redirect:/settings";
	}
	
}
