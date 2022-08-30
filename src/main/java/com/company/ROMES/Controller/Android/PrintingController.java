package com.company.ROMES.Controller.Android;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.ROMES.entity.LabelPrinter;
import com.company.ROMES.entity.Lot;
import com.company.ROMES.entity.ManufactureProduct;
import com.company.ROMES.entity.Material;
import com.company.ROMES.entity.OrderHistory;
import com.company.ROMES.functions.PrintingObject;
import com.company.ROMES.functions.SessionMethod;

import Error_code.ResultCode;

@Controller
public class PrintingController {
	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/Android/printList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getPrintServiceList() {
		Session session = null;
		JSONObject object = new JSONObject();
		try {
			session = factory.openSession();
			List<LabelPrinter> printers = session.createQuery("Select this_ from LabelPrinter this_ order by this_.id")
					.getResultList();
			JSONArray arry = new JSONArray();
			for (LabelPrinter printer : printers) {
				JSONObject ob = new JSONObject();
				ob.put("id", printer.getId());
				ob.put("name", printer.getPrinterName());
				arry.add(ob);
			}
			object.put("data", arry);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session);
		}
		return object.toJSONString();
	}

	@RequestMapping(value = "/Android/printLabel", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String printLot(@RequestParam(name = "id") int id, @RequestParam("printer") int printerid,
			int margin_w, int margin_h, int fontsize) {
		JSONObject object = new JSONObject();
		List<String> list = new ArrayList<>();

		Session session = null;
		Transaction transaction = null;
		try {
			
			session = factory.openSession();
			transaction = session.beginTransaction();
			PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
			PrintService service = null;
			LabelPrinter printer = session.find(LabelPrinter.class, printerid);
			Lot lot = session.find(Lot.class, id);
			ManufactureProduct product = session.find(ManufactureProduct.class, lot.getProductId());
			String prname = "";
			if (product == null) {
				Material m = session.find(Material.class, lot.getProductId());
				prname = m.getName();
			}else
				prname = product.getName();
			PrintingObject printobj = new PrintingObject();
			printobj.addPrintingLine(prname);
			printobj.addPrintingLine(lot.getLot());
			printobj.setBarcode(lot.getLot());
			printobj.print(printer.getPrinterName());
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionMethod.closeSession(session, transaction);
		}

		return object.toJSONString();
	}
}
