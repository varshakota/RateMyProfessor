package extended.cs.sdsu.edu;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SelectedProfessorDetailsActivity extends ListActivity {

	private TextView firstNameTextView;
	private TextView lastNameTextView;
	private TextView officeTextView;
	private TextView phoneTextView;
	private TextView emailTextView;
	private TextView averageTextView;
	private TextView totalRatingsTextView;
	private ProfessorService professorService;
	private int selectedProfessorId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selected_professor_details);

		firstNameTextView = (TextView) findViewById(R.id.firstName);
		lastNameTextView = (TextView) findViewById(R.id.lastName);
		officeTextView = (TextView) findViewById(R.id.office);
		phoneTextView = (TextView) findViewById(R.id.phone);
		emailTextView = (TextView) findViewById(R.id.email);
		averageTextView = (TextView) findViewById(R.id.average);
		totalRatingsTextView = (TextView) findViewById(R.id.totalRatings);

		Bundle details = getIntent().getExtras();
		selectedProfessorId = details.getInt("selectedProfessorId");
		professorService = new ProfessorService();
	}

	public void rateProfessor(View click) {
		Bundle details = getIntent().getExtras();
		int selectedProfessorId = details.getInt("selectedProfessorId");
		Intent rateProfessor = new Intent();
		rateProfessor.setClassName("extended.cs.sdsu.edu",
				"extended.cs.sdsu.edu.RateProfessorActivity");
		rateProfessor.setAction("cs.assignment.intent.action.RATE_PROFESSOR");
		rateProfessor.putExtra("selectedProfessorId", selectedProfessorId);
		startActivity(rateProfessor);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshProfessorRatings();
		refreshProfessorCommentsList();
	}

	private void refreshProfessorCommentsList() {
		List<Professor> professorComments = new ArrayList<Professor>();
		try {
			professorComments = professorService
					.getProfessorComments(selectedProfessorId);
			ProfessorCommentsAdapter professorCommentsAdapter = new ProfessorCommentsAdapter(
					professorComments, this);
			setListAdapter(professorCommentsAdapter);
			professorCommentsAdapter.refreshList(professorComments);
			professorCommentsAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Log.e("RateMyProfessor", e.getMessage(), e);
		}
	}

	private void refreshProfessorRatings() {
		Professor professorDetails;
		try {
			professorDetails = professorService
					.getprofessorDetails(selectedProfessorId);

			firstNameTextView.setText(professorDetails.getFirstName());
			lastNameTextView.setText(professorDetails.getLastName());
			officeTextView.setText(professorDetails.getOffice());
			phoneTextView.setText(professorDetails.getPhone());
			emailTextView.setText(professorDetails.getEmail());
			float average2 = professorDetails.getAverage();
			Float faverage = new Float(average2);
			String straverage = faverage.toString();
			averageTextView.setText(straverage);
			int iTotalRatings = professorDetails.getTotalRatings();
			Integer totalRatingInt = new Integer(iTotalRatings);
			String totalRating = totalRatingInt.toString();
			totalRatingsTextView.setText(totalRating);
		} catch (Exception e) {
			Log.e("RateMyProfessor", e.getMessage(), e);
		}
	}
}
