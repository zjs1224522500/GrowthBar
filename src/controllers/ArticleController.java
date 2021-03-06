package controllers;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import common.model.Article;
import common.model.UserInformation;

import services.ArticleServices;
import utils.DateHelper;

import java.util.List;

public class ArticleController extends Controller implements BaseController {

	private ArticleServices articleServices = new ArticleServices();

	public void index() {

		render("Art_index.html");
	}

	public void add() {

		render("Art_edit.html");
	}

	/**
	 * 保存文章 or 发表文章 or 修改文章
	 */
	public void save() {
		boolean saveSuccess = false;
		Article article = new Article();
		UserInformation userInformation = getSessionAttr("user");
		if (null == userInformation) {
			setAttr("status", false);
		} else {
			article.setUserAccount(userInformation.getUserAccount());
			String content = getPara("art_content");
			article.setArticleContent(content);
			String title = getPara("art_name");
			article.setArticleTitle(title);

			//		Integer status = getParaToInt("status");
			Integer status = SUBMITTED;
			if (SAVED.equals(status) || SUBMITTED.equals(status)) {
				article.setStatus(status);
			}

			article.setPostTime(DateHelper.getDateTime());

			saveSuccess = articleServices.save(article);

			setAttr("article", article);
			setAttr("status", saveSuccess);
		}
		renderJson();
	}

	public void queryOwnArticles() {

		boolean querySuccess = false;
		String status = getPara("status", "submitted");

		Integer pageNum = getParaToInt("pageNum", 1);
		UserInformation userInformation = getSessionAttr("user");
		String userId = (null == userInformation ? null : userInformation.getUserAccount());
		if (null != userId && !"".equals(userId)) {
			if ("submitted".equals(status)) {
				setAttr("articleList", articleServices.queryByUserId(userId, pageNum, 7));
			} else if ("saved".equals(status)) {
				setAttr("articleList", articleServices.queryOwnSavedArticles(userId, pageNum, 7));
			} else {
				return;
			}
			querySuccess = true;
		}
		setAttr("status", querySuccess);
		renderJson();
	}

	public void viewOwn() {
		UserInformation userInformation = getSessionAttr("user");
		String userId = (null == userInformation ? null : userInformation.getUserAccount());
		if (null != userId && !"".equals(userId)) {
			setAttr("articles", articleServices.queryByUserId(userId, 1, 10000).getList());
		}
		setAttr("status",true);
		renderJson();
	}

	/**
	 * 查询相关文章 条件查询
	 */
	public void query() {

		boolean querySuccess = false;
		Integer pageNum = getParaToInt("pageNum", 1);

		String artName = getPara("artName", "");
		if (null != artName) {
			setAttr("articleList", articleServices.queryByArtName(artName, pageNum, 7));
			querySuccess = true;
		}

		UserInformation userInformation = getSessionAttr("user");
		String userId = (null == userInformation ? null : userInformation.getUserAccount());
		if (null != userId && !"".equals(userId)) {
			setAttr("articleList", articleServices.queryByUserId(userId, pageNum, 7));
			querySuccess = true;
		}
		setAttr("status", querySuccess);
		renderJson();
	}

	/**
	 * 预览文章列表
	 */
	public void viewArticles() {

		Page<Article> articlesPage = null;

		Integer pageNum = getParaToInt("pageNum", 1);

		boolean queryStatus = false;

		articlesPage = articleServices.paginate(pageNum, 7);
		if (null != articlesPage.getList()) {
			List<Article> articleList = articlesPage.getList();
			setAttr("articles", articleList);
			queryStatus = true;
		}

		setAttr("status", queryStatus);
		renderJson();
	}

	/**
	 * 查看某篇文章
	 */
	public void viewArticle() {

		Integer artId = getParaToInt("artId");
		boolean querySuccess = false;

		if (null != artId) {
			Article article = articleServices.select(artId);
			if (null != article) {
				setAttr("article", article);
			}
			querySuccess = true;
		}
		setAttr("status", querySuccess);
		renderJson();
	}

	/**
	 * 用户删除自己的文章
	 */
	public void delete() {

		//TODO 用户删除权限验证
		UserInformation userInformation = getSessionAttr("user");
		String userAccount = null == userInformation ? null : userInformation.getUserAccount();
		Integer artId = getParaToInt("artId", 0);
		boolean deleteSuccess = false;
		Article article = articleServices.select(artId);
		if (null != article && article.getUserAccount().equals(userAccount)) {
			deleteSuccess = articleServices.remove(artId);
			setAttr("article", article);
		}
		setAttr("status", deleteSuccess);
		renderJson();
	}

}
