package com.github.binarywang.demo.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMultimap;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@RestController
@RequestMapping("/wechat/message")
public class MessageController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxMpService wxService;

	@Autowired
	private WxMpMessageRouter router;

	@PostMapping(produces = "application/json; charset=UTF-8")
	public Object post(@RequestBody String requestBody,
			@RequestParam(name = "content", required = false) String content) throws WxErrorException {
		
		System.err.println(requestBody);
		System.err.println(content);
		
		WxMpKefuMessage message = WxMpKefuMessage.TEXT().toUser("OPENID")
				.content("I am wanglvyihua.").build();
		wxService.getKefuService().sendKefuMessage(message);
		Map<String, Object> result = new HashMap<>();
		result.put("code", 0);
		result.put("msg", "success");
		return result;
	}

	private WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}
}
