package userplugin;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import userplugin.handlers.EditorListener;

public class Startup implements IStartup {
	
	@Override
	public void earlyStartup() {
		final IWorkbenchWindow[] workbenchWindows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		EditorListener eventListener = new EditorListener();

		for (IWorkbenchWindow window : workbenchWindows) {
			window.getPartService().addPartListener(eventListener);
			
			
		}
	}
}
