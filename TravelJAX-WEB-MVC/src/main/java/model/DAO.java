package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

import java.util.ArrayList;
import java.util.Date;
public class DAO {
      private DataSource ds;

// DEFINICIJA KONEKCIONIH STRINGOVA
      private static String PUTOVANJE = "SELECT pid, k.id_korisnika, k.ime, k.prezime, d.id_destinacije, d.naziv, vreme_polaska, \r\n"
  			+ "datum_povratka, duzina_puta, cena_puta, pr.id_prevoza, pr.naziv \r\n"
  			+ "FROM putovanje p JOIN korisnik k ON p.putnik_id = k.id_korisnika \r\n"
  			+ "JOIN destinacija d ON p.destinacija_id = d.id_destinacije \r\n"
  			+ "JOIN prevoz pr ON p.id_prevoz = pr.id_prevoza";
  	
  	private static String BrisanjePutovanja = "DELETE FROM `putovanje` WHERE pid = ?";
  	private static String InsertPutovanje = "INSERT INTO `putovanje` (`putnik_id`, `destinacija_id`, `vreme_polaska`, `datum_povratka`, `duzina_puta`, `cena_puta`, `id_prevoz`) \r\n"
  			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
  	private static String SELECTKORISNICI = "SELECT * FROM korisnik";
  	private static String SELECTDESTINACIJE = "SELECT * FROM `destinacija`";
  	private static String TYPEOFTRANSPORT = "SELECT * FROM prevoz";
  	private static String SELECTIDPREVOZA = "SELECT id_prevoza FROM `prevoz` WHERE naziv = ?";
  	private static String SELECTUSERBYIDANDPASSWORD = "SELECT * FROM `korisnik` WHERE korisnicko_ime = ? AND lozinka = ?";
	
	// DEFINICIJA KONSTRUKTORA ZA PODESAVNJE KONEKCIJE – UVEK ISTO
	public DAO(){
	try {
		InitialContext cxt = new InitialContext();
		if ( cxt == null ) { 
		} 
		ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/mysql" ); 
		if ( ds == null ) { 
		} 		
		} catch (NamingException e) {
		}
	}
	
	public ArrayList<Korisnik> selecKorisnik(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Korisnik> lo = new ArrayList<Korisnik>();
		Korisnik k = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTKORISNICI);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_korisnika");
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				String korisnickoIme = rs.getString("korisnicko_ime");
				String lozinka = rs.getString("lozinka");
				 k = new Korisnik(id, ime, prezime, korisnickoIme, lozinka);
				lo.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	
	
	public Korisnik selecKorisnikByIdAndPassword(String username, String password){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Korisnik> lo = new ArrayList<Korisnik>();
		Korisnik k = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTUSERBYIDANDPASSWORD);
			pstm.setString(1, username);
			pstm.setString(2, password);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_korisnika");
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				String korisnickoIme = rs.getString("korisnicko_ime");
				String lozinka = rs.getString("lozinka");
				 k = new Korisnik(id, ime, prezime, korisnickoIme, lozinka);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k; 
	}
	
	
	public ArrayList<Destinacija> selectDestinacija(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Destinacija> lo = new ArrayList<Destinacija>();
		Destinacija d = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTDESTINACIJE);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_destinacije");
				String naziv = rs.getString("naziv");
				 d = new Destinacija(id, naziv);
				lo.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	
	public ArrayList<VrstaPrevoza> prevoz(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<VrstaPrevoza> lo = new ArrayList<VrstaPrevoza>();
		VrstaPrevoza vp = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(TYPEOFTRANSPORT);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_prevoza");
				String naziv = rs.getString("naziv");
				vp = new VrstaPrevoza(id, naziv);
				lo.add(vp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	
	public ArrayList<Putovanje> putovanje(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Putovanje> lo = new ArrayList<Putovanje>();
		Putovanje put = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(PUTOVANJE);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int idPutovanja = rs.getInt("pid");
				
				int idKorisnika = rs.getInt("id_korisnika");
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				Korisnik k = new Korisnik();
				k.setId(idKorisnika);
				k.setIme(ime);
				k.setPrezime(prezime);
				
				int idDestinacije = rs.getInt("id_destinacije");
				String naziv = rs.getString("naziv");
				Destinacija d = new Destinacija(idDestinacije, naziv);
				
				Date vremePolaska = rs.getDate("vreme_polaska");
				Date datumPovratka = rs.getDate("datum_povratka");
				int duzinaPuta = rs.getInt("duzina_puta");
				double cena = rs.getDouble("cena_puta");
				
				int idPrevoza = rs.getInt("id_prevoza");
				String nazivPrevoza = rs.getString(12);
				VrstaPrevoza vp = new VrstaPrevoza(idPrevoza, nazivPrevoza);
				
				 put = new Putovanje(idPutovanja, k, d, vremePolaska, datumPovratka, duzinaPuta, cena, vp);
				lo.add(put);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	
	public void insertPutovanja (Putovanje p){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String vremePolaska = sdf.format(p.getVremePolaska());
		String datumPovratka = sdf.format(p.getDatumPovratka());
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(InsertPutovanje);
			pstm.setInt(1, p.getK().getId());
			pstm.setInt(2, p.getD().getId());
			pstm.setString(3, vremePolaska);
			pstm.setString(4, datumPovratka);
			pstm.setInt(5, p.getDuzinaPuta());
			pstm.setDouble(6, p.getCena());
			pstm.setInt(7, p.getP().getIdPrevoza());
			pstm.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void brisanjePutovanja (int id){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(BrisanjePutovanja);
			pstm.setInt(1, id);
			pstm.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertPutovanjeID (Putovanje p){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer id = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String vremePolaska = sdf.format(p.getVremePolaska());
		String datumPovratka = sdf.format(p.getDatumPovratka());
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(InsertPutovanje, Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, p.getK().getId());
			pstm.setInt(2, p.getD().getId());
			pstm.setString(3, vremePolaska);
			pstm.setString(4, datumPovratka);
			pstm.setInt(5, p.getDuzinaPuta());
			pstm.setDouble(6, p.getCena());
			pstm.setInt(7, p.getP().getIdPrevoza());
			pstm.execute();
			
			rs = pstm.getResultSet();
			ResultSet keys = pstm.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public int select_id_prevoza(String naziv){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer id = null;
		
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTIDPREVOZA);
			pstm.setString(1, naziv);
			pstm.execute();
			
			rs = pstm.getResultSet();
			
			if(rs.next()) {
				id = rs.getInt("id_prevoza");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
