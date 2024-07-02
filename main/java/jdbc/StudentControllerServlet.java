package jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Define the reference to the StudentDbUtil class
	private StudentDbUtil studentDbUtil;

	// Define the reference to the DataSource
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		// Create the StudentDbUtil object and pass in the DataSource object
		super.init();
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// list the students ... in MVC fashion
		try {
			listStudents(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Get the students from the StudentDbUtil
		List<Student> students = studentDbUtil.getStudents();

		try {
			// Add the students to the request
			request.setAttribute("STUDENT_LIST", students);

			// Send to JSP page (view)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
			dispatcher.forward(request, response);

		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}

	}

}
