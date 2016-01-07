package update.center.controllers;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import update.center.manager.ModalDialogProviderBean;
import version.IModalDailogProvider;
import version.ModalDialog;

public class AppUtil {

	  public static ValueExpression  createValueExpression(String valueExpression, Class<?> valueType) {
		  	FacesContext fc = FacesContext.getCurrentInstance();
			return fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(), valueExpression, valueType);
	  }
	  
	  public static IModalDailogProvider getProvider(){
		  return new IModalDailogProvider() {
			@Override
			public ModalDialog getModalDialog() {
				// TODO Auto-generated method stub
				 FacesContext fc = FacesContext.getCurrentInstance();
				 ValueExpression expression = createValueExpression("#{modalDialogProviderBean}", ModalDialogProviderBean.class);
				 return ((ModalDialogProviderBean) expression.getValue(fc.getELContext())).getModalDialog();
			}
		};
	  }
}
