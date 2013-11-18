package com.desktop.action;

import java.lang.reflect.Field;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.desktop.utils.EntityUtil;
import com.desktop.utils.ModelUtil;
import com.desktop.utils.StringUtil;

@Controller
@Scope("prototype")
public class ModelAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getModel() {
		return null;
	}
	
	public void getModelFields() {
		if(StringUtil.isEmpty(ModelUtil.modelJson.get(modelName))) {
			Class<?> c = EntityUtil.getClassByName(modelName);
			Field[] fields = ModelUtil.getClassFields(c, false);
			strData = jsonBuilder.getModelFileds(modelName,fields,excludes);
		} else {
			strData = ModelUtil.modelJson.get(modelName);
		}
		
		toWrite(strData);
	}

}
