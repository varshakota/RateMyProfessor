package extended.cs.sdsu.edu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProfessorCommentsAdapter extends BaseAdapter {
	private List<Professor> professorComments;
	private final Context context;

	public ProfessorCommentsAdapter(List<Professor> professorComments,
			Context context) {
		this.professorComments = professorComments;
		this.context = context;

	}

	@Override
	public int getCount() {
		return professorComments.size();
	}

	@Override
	public Object getItem(int position) {
		return professorComments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return professorComments.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.comments_listview_row, null);
			holder.commentsText = (TextView) convertView
					.findViewById(R.id.comments);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Professor professor = (Professor) getItem(position);
		holder.commentsText.setText(professor.getText() + "   "
				+ professor.getDate());

		return convertView;
	}

	public void refreshList(List<Professor> professorComments) {
		this.professorComments = professorComments;
	}

	private static class ViewHolder {
		TextView commentsText;
	}

}
