package update.center.manager;

import version.IModalDailogProvider;
import version.ModalDialog;


public class ModalDialogProviderBean implements IModalDailogProvider {

	private  ModalDialog modalDialog = new ModalDialog();
	
	public ModalDialogProviderBean(){
		System.out.println("=============================== ModalDialogProviderBean initialized =================== ");
	}
	
	@Override
	public ModalDialog getModalDialog() {
		// TODO Auto-generated method stub
		return modalDialog;
	}
	
}
