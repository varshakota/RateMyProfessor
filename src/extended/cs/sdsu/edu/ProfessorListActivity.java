package extended.cs.sdsu.edu;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ProfessorListActivity extends ListActivity {

	private ProfessorListAdapter professorListAdapter;
	private List<Professor> professorList = new ArrayList<Professor>();
	private ProfessorService professorService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		professorService = new ProfessorService();
		refreshProfessorList(professorService);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		Professor professor = (Professor) listView.getItemAtPosition(position);
		int selectedProfessorId = professor.getId();

		Intent selectedProfessorDetails = new Intent();
		selectedProfessorDetails.setClassName("extended.cs.sdsu.edu",
				"extended.cs.sdsu.edu.SelectedProfessorDetailsActivity");
		selectedProfessorDetails
				.setAction("cs.assignment.intent.action.VIEW_PROFESSOR_DETAILS");
		selectedProfessorDetails.putExtra("selectedProfessorId",
				selectedProfessorId);
		startActivity(selectedProfessorDetails);
	}

	private void refreshProfessorList(ProfessorService professorService) {
		try {
			professorList = professorService.getProfessorList();

			professorListAdapter = new ProfessorListAdapter(professorList, this);
			setListAdapter(professorListAdapter);
			professorListAdapter.refreshList(professorList);
			professorListAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Log.e("RateMyProfessor", e.getMessage(), e);
		}
	}
}