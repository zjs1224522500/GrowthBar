package controllers;

import com.jfinal.core.Controller;

import common.model.ExamResult;
import common.model.UserInformation;

import services.ExamresultServices;

public class ExamresultController extends Controller implements BaseController {

	private ExamresultServices examresultServices = new ExamresultServices();

	public boolean storeScore() {
		boolean saveSuccess = false;
		UserInformation userInformation = getSessionAttr("user");
		String userAccount = userInformation.getUserAccount();
		String quTiHua = getPara("quTiHua");
		String qiangPo = getPara("qiangPo");
		String jiaoLv = getPara("jiaoLv");
		String diDui = getPara("diDui");
		String kongBu = getPara("kongBu");
		String pianZhi = getPara("pianZhi");
		String jingShenBing = getPara("jingShenBing");
		String shuiMian = getPara("shuiMian");
		ExamResult examResult = new ExamResult();
		examResult.setUserAccount(userAccount);
		examResult.setUserAccount(userAccount);
		examResult.setQutihua(quTiHua);
		examResult.setQiangpo(qiangPo);
		examResult.setJiaolv(jiaoLv);
		examResult.setDidui(diDui);
		examResult.setKongbu(kongBu);
		examResult.setPianzhi(pianZhi);
		examResult.setJingshenbing(jingShenBing);
		examResult.setShuimian(shuiMian);
		saveSuccess = examresultServices.save(examResult);
		return saveSuccess;
	}
}
