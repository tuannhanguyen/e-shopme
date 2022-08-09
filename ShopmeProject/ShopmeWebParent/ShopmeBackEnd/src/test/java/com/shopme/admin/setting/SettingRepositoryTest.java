package com.shopme.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTest {

	@Autowired SettingRepository repository;
	
//	@Test
//	public void testCreateGenerealSetting() {
//		//Setting siteName = new Setting("SITE_NAME", "Shopme", SettingCategory.GENERAL);
//		//Setting savedSetting = repository.save(siteName);
//		Setting siteLogo = new Setting("SITE_LOGO", "Shopme.png", SettingCategory.GENERAL);
//		Setting copyright = new Setting("COPY_RIGHT", "Copy right (C) 2021 Shopme Ltd.", SettingCategory.GENERAL);
//		
//		repository.saveAll(List.of(siteLogo, copyright));
//		
//		Iterable<Setting> iterable = repository.findAll();
//		
//		assertThat(iterable).size().isGreaterThan(0);
//	}
//	
//	@Test
//	public void testCreateCurrencySetting() {
//		Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
//		Setting symbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
//		Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
//		Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
//		Setting decimalDegits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
//		Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);
//		
//		repository.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType, decimalDegits, thousandsPointType));
//		
//	}
	
	@Test
	public void testListSettingByCategory() {
		List<Setting> settings = repository.findByCategory(SettingCategory.GENERAL);
		
		settings.forEach(System.out :: println);
	}
}
