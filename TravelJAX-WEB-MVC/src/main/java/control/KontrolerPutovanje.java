package control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.Destinacija;
import model.Korisnik;
import model.Putovanje;
import model.VrstaPrevoza;

@WebServlet("/Putnik")
public class KontrolerPutovanje extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KontrolerPutovanje() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("id");

		String action = request.getParameter("action");
		if (action != null && action.trim().length() > 0) {
			switch (action) {
			case "Delete":

				DAO dao = new DAO();

				try {
					int id = Integer.parseInt(ids);
					dao.brisanjePutovanja(id);
					request.setAttribute("msg", "Uspesno izbrisano putovanje id= " + id);
					request.getRequestDispatcher("podaciOPutovanju.jsp").forward(request, response);
				} catch (Exception e) {
					request.setAttribute("msg", "Doslo je do greske");
					request.getRequestDispatcher("podaciOPutovanju.jsp").forward(request, response);
				}
				break;
			case "Logout":
				request.getSession().invalidate();
				response.sendRedirect("index.jsp");
			}

		} else {
			response.getWriter().println("Error 403, Bad request");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao = new DAO();

		String action = request.getParameter("action");
		if (action != null && action.trim().length() > 0) {
			switch (action) {
			case "Dodaj":
				String id_korisnika = request.getParameter("imePrezime");
				String id_destinacije = request.getParameter("destinacija");
				String datumPolaska = request.getParameter("datum_polaska");
				String datum_povratka = request.getParameter("datum_povratka");
				String duzina = request.getParameter("duzina");
				String cena = request.getParameter("cena");
				String prevoz = request.getParameter("test");

				if (id_korisnika != null && id_korisnika.trim().length() > 0 && id_destinacije != null
						&& id_destinacije.trim().length() > 0 && datumPolaska != null
						&& datumPolaska.trim().length() > 0 && datum_povratka != null
						&& datum_povratka.trim().length() > 0 && duzina != null && duzina.trim().length() > 0
						&& cena != null && cena.trim().length() > 0 && prevoz != null && prevoz.trim().length() > 0) {

					try {
						int idKorisnika = Integer.parseInt(id_korisnika);
						int idDestinacije = Integer.parseInt(id_destinacije);
						Date dtPolaska = new SimpleDateFormat("yyyy-MM-dd").parse(datumPolaska);
						Date dtPovratka = new SimpleDateFormat("yyyy-MM-dd").parse(datum_povratka);
						if (!dtPovratka.before(dtPolaska)) {
							int duzinaPuta = Integer.parseInt(duzina);
							double cenaD = Double.parseDouble(cena);
							Korisnik k = new Korisnik();
							k.setId(idKorisnika);
							Destinacija d = new Destinacija();
							d.setId(idDestinacije);
							VrstaPrevoza vp = new VrstaPrevoza();
							vp.setIdPrevoza(dao.select_id_prevoza(prevoz));
							vp.setNazivPrevoza(prevoz);

							Putovanje p = new Putovanje(0, k, d, dtPolaska, dtPovratka, duzinaPuta, cenaD, vp);
							int idPutovanja = dao.insertPutovanjeID(p);

							request.setAttribute("msg", "Uspesan insert putovanja");
							request.getRequestDispatcher("podaciOPutovanju.jsp").forward(request, response);
						} else {
							request.setAttribute("msg", "Pogresan datum");
							request.getRequestDispatcher("podaciOPutovanju.jsp").forward(request, response);
						}

					} catch (Exception e) {
						request.setAttribute("msg", "Netacan format podataka");
						request.getRequestDispatcher("podaciOPutovanju.jsp").forward(request, response);
					}

				} else {
					request.setAttribute("msg", "Polja moraju biti popunjena");
					request.getRequestDispatcher("podaciOPutovanju.jsp").forward(request, response);
				}
				break;
			case "Login":
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				if (username != null && username.trim().length() > 0 && password != null
						&& password.trim().length() > 0) {
					Korisnik korisnik = dao.selecKorisnikByIdAndPassword(username, password);

					if (korisnik != null) {
						request.getSession().setAttribute("korisnik", korisnik);
						response.sendRedirect("podaciOPutovanju.jsp");
					} else {
						request.setAttribute("msg", "Netacan username ili sifra");
						request.getRequestDispatcher("index.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("msg", "Polja moraju biti popunjena");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			}
		} else {
			request.setAttribute("msg", "Nema akcije");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}
