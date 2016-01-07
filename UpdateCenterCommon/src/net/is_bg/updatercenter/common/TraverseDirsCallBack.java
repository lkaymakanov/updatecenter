package net.is_bg.updatercenter.common;

import java.io.File;

public interface TraverseDirsCallBack {
		
	public void OnForward(File node);
	public void OnReturnFromRecursion(File node);
}
