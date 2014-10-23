package userplugin.handlers;

import java.sql.SQLException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.IEditorReference;

import userplugin.Activator;


public class EditorListener extends ParentListener implements IPartListener2 {

	public StyledTextContent content;
	private String username = Activator.identification;
	
	
	public EditorListener() {
	}
	
	public Object execute(ExecutionEvent event) {
		return null;
	}


	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		
	}

	@Override
	public void partBroughtToTop(final IWorkbenchPartReference partRef) {
		
		final String fileName = getActiveFileName(partRef);
		final String projectName = getActiveProjectName(partRef);
		
		try {
			sqlUpdate(username, 0, fileName, projectName);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		AbstractTextEditor e = (AbstractTextEditor)((IEditorReference) partRef).getEditor(false);
		final StyledText textfield = ((StyledText)e.getAdapter(Control.class));
		textfield.addCaretListener(new CaretListener() {

			@Override
			public void caretMoved(CaretEvent event) {
				try {
					int lineNum = getLineNumber(textfield, event);
					sqlUpdate(username, lineNum, fileName, projectName);
					// Test
					//if (lineNum == 7) 
					//	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("userplugin.views.GreetingView");
					// End test
				} catch (SQLException e) {
					e.printStackTrace();
				} //catch (PartInitException e) {
				//	e.printStackTrace();
				//}
			}
	        	
		});
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		
		
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		//System.out.println(getActiveProjectName(partRef));
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		
		
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		
		
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		
		
	}
	
	
}
