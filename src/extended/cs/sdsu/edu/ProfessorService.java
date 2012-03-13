package extended.cs.sdsu.edu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfessorService {
	private JSONObjectMapper jsonObjectMapper;

	public ProfessorService() {
		jsonObjectMapper = new JSONObjectMapper();
	}

	public List<Professor> getProfessorList() throws InterruptedException,
			ExecutionException {
		List<Professor> professorList = new ArrayList<Professor>();
		String url = "http://bismarck.sdsu.edu/rateme/list";
		NetworkConnection networkConnection = new NetworkConnection();
		String responseBody = networkConnection.execute(url).get();
		if (responseBody != null) {
			JSONArray jsonProfessorArray;
			try {
				jsonProfessorArray = new JSONArray(responseBody);
				professorList.addAll(jsonObjectMapper
						.jsonProfessorArrayToList(jsonProfessorArray));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return professorList;
	}

	public Professor getprofessorDetails(int selectedProfessorId)
			throws JSONException, InterruptedException, ExecutionException {
		String url = "http://bismarck.sdsu.edu/rateme/instructor/"
				+ selectedProfessorId;
		NetworkConnection networkConnection = new NetworkConnection();
		String responseBody = networkConnection.execute(url).get();
		JSONObject jsonObject = new JSONObject(responseBody);
		Professor professorDetails = new Professor();
		professorDetails = jsonObjectMapper
				.covertJsonObjectToProfessor(jsonObject);
		return professorDetails;
	}

	public List<Professor> getProfessorComments(int selectedProfessorId)
			throws InterruptedException, ExecutionException, JSONException {
		String url = "http://bismarck.sdsu.edu/rateme/comments/"
				+ selectedProfessorId;
		NetworkConnection networkConnection = new NetworkConnection();
		String responseBody = networkConnection.execute(url).get();
		JSONArray jsonArrayComments = new JSONArray(responseBody);
		JSONObjectMapper jsonObjectmapper = new JSONObjectMapper();
		List<Professor> commentsList = new ArrayList<Professor>();
		commentsList.addAll(jsonObjectmapper
				.getSelectedProfessorCommentsArrayToList(jsonArrayComments));
		return commentsList;
	}

	public HttpResponse submitProfessorComments(int selectedProfessorId,
			String comments) throws InterruptedException, ExecutionException {
		String url = "http://bismarck.sdsu.edu/rateme/comment/"
				+ selectedProfessorId;
		PostNetworkConnection netowrkConnection = new PostNetworkConnection();
		HttpResponse httpResponse = netowrkConnection.execute(url, comments)
				.get();
		return httpResponse;
	}

	public HttpResponse submitProfessorRating(int selectedProfessorId,
			String rating) throws InterruptedException, ExecutionException {
		String url = "http://bismarck.sdsu.edu/rateme/rating/"
				+ selectedProfessorId + "/" + rating;
		PostNetworkConnection netowrkConnection = new PostNetworkConnection();
		HttpResponse httpResponse = netowrkConnection.execute(url, rating)
				.get();
		return httpResponse;

	}

	public Professor getProfessorRating(int selectedProfessorId, String rating)
			throws InterruptedException, ExecutionException, JSONException {
		String url = "http://bismarck.sdsu.edu/rateme/rating/"
				+ selectedProfessorId + "/" + rating;
		NetworkConnection networkConnection = new NetworkConnection();
		String responseBody = networkConnection.execute(url).get();
		JSONObject jsonObject = new JSONObject(responseBody);
		Professor professorDetails = new Professor();
		professorDetails = jsonObjectMapper.getRating(jsonObject);
		return professorDetails;
	}
}
