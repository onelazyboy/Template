/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tools.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.tools.utils.TwoDimensionCode;

/**
 * 二维码Controller
 * @author jeeplus
 * @version 2015-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/TwoDimensionCodeController")
public class TwoDimensionCodeController extends BaseController {

	@Autowired
	private SystemService systemService;
	/**
	 * 二维码页面
	 */
	@RequestMapping(value = {"index", ""})
	public String index() throws Exception{
		return "modules/tools/TwoDimensionCode";
	}
	
	/**
	 *	生成二维码
	 * @param args
	 * @throws Exception
	 */
	@RequestMapping(value="createTwoDimensionCode")
	@ResponseBody
	public AjaxJson createTwoDimensionCode(HttpServletRequest request, String encoderContent){
		AjaxJson j = new AjaxJson();
		Principal principal = (Principal) UserUtils.getPrincipal();
		User user = UserUtils.getUser();
		if (principal == null){
			j.setSuccess(false);
			j.setErrorCode("0");
			j.setMsg("没有登录");
		}
		String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
							+ principal + "/qrcode/";
		FileUtils.createDirectory(realPath);
		String name="test.png"; //encoderImgId此处二维码的图片名
			try {
				String filePath = realPath + name;  //存放路径
				TwoDimensionCode.encoderQRCode(encoderContent, filePath, "png");//执行生成二维码
				user.setQrCode(request.getContextPath()+Global.USERFILES_BASE_URL
						+ principal + "/qrcode/"+name);
				systemService.updateUserInfo(user);
				j.setSuccess(true);
				j.setMsg("二维码生成成功");
				j.put("filePath", request.getContextPath()+Global.USERFILES_BASE_URL
						+ principal + "/qrcode/"+name);
			} catch (Exception e) {
				
			}
		return j;
	}
//	
//	/**
//	 *	解析二维码
//	 * @param args
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/readTwoDimensionCode")
//	@ResponseBody
//	public Object readTwoDimensionCode(){
//		Map<String,String> map = new HashMap<String,String>();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		String errInfo = "success",readContent="";
//		String imgId = pd.getString("imgId");//内容
//		if(null == imgId){
//			errInfo = "error";
//		}else{
//			try {
//				String filePath = PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE + imgId;  //存放路径
//				readContent = TwoDimensionCode.decoderQRCode(filePath);//执行读取二维码
//			} catch (Exception e) {
//				errInfo = "error";
//			}
//		}
//		map.put("result", errInfo);						//返回结果
//		map.put("readContent", readContent);			//读取的内容
//		return AppUtil.returnObject(new PageData(), map);
//	}
//	

}