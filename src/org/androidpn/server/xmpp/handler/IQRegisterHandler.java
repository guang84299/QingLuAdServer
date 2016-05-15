/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;

import net.sf.json.JSONObject;

import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserExistsException;
import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketError;

import com.qinglu.ad.model.Area;
import com.qinglu.ad.model.NetworkOperator;
import com.qinglu.ad.model.PhoneModel;
import com.qinglu.ad.model.User;
import com.qinglu.ad.service.AreaService;
import com.qinglu.ad.service.NetworkOperatorService;
import com.qinglu.ad.service.PhoneModelService;
import com.qinglu.ad.service.UserService;

/**
 * This class is to handle the TYPE_IQ jabber:iq:register protocol.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class IQRegisterHandler extends IQHandler {

	private static final String NAMESPACE = "jabber:iq:register";

	private UserService userService;
	private PhoneModelService phoneModelService;
	private AreaService areaService;
	private NetworkOperatorService networkOperatorService;

	private Element probeResponse;

	/**
	 * Constructor.
	 */
	public IQRegisterHandler() {
		userService = ServiceLocator.getUserService();
		phoneModelService = (PhoneModelService) ServiceLocator.getService("phoneModelService");
		areaService =  (AreaService) ServiceLocator.getService("areaService");
		networkOperatorService = (NetworkOperatorService) ServiceLocator.getService("networkOperatorService");
		
		probeResponse = DocumentHelper.createElement(QName.get("query",
				NAMESPACE));
		probeResponse.addElement("username");
		probeResponse.addElement("password");
		probeResponse.addElement("deviceId");
		probeResponse.addElement("phoneNumber");
		probeResponse.addElement("networkOperatorName");
		probeResponse.addElement("simSerialNumber");
		probeResponse.addElement("networkCountryIso");
		probeResponse.addElement("networkOperator");
		probeResponse.addElement("networkType");
		probeResponse.addElement("location");
		probeResponse.addElement("phoneType");
		probeResponse.addElement("model");
		probeResponse.addElement("release");
	}

	/**
	 * Handles the received IQ packet.
	 * 
	 * @param packet
	 *            the packet
	 * @return the response to send back
	 * @throws UnauthorizedException
	 *             if the user is not authorized
	 */
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		IQ reply = null;

		ClientSession session = sessionManager.getSession(packet.getFrom());
		if (session == null) {
			log.error("Session not found for key " + packet.getFrom());
			reply = IQ.createResultIQ(packet);
			reply.setChildElement(packet.getChildElement().createCopy());
			reply.setError(PacketError.Condition.internal_server_error);
			return reply;
		}

		if (IQ.Type.get.equals(packet.getType())) {
			reply = IQ.createResultIQ(packet);
			if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
				// TODO
			} else {
				reply.setTo((JID) null);
				reply.setChildElement(probeResponse.createCopy());
			}
		} else if (IQ.Type.set.equals(packet.getType())) {
			try {
				Element query = packet.getChildElement();
				if (query.element("remove") != null) {
					if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
						// TODO
					} else {
						throw new UnauthorizedException();
					}
				} else {
					String username = query.elementText("username");
					String password = query.elementText("password");
					String email = query.elementText("email");
					String name = query.elementText("name");
					String deviceId = query.elementText("deviceId");
					String phoneNumber = query.elementText("phoneNumber");
					String networkOperatorName = query
							.elementText("networkOperatorName");
					String simSerialNumber = query
							.elementText("simSerialNumber");
					String networkCountryIso = query
							.elementText("networkCountryIso");
					String networkOperator = query
							.elementText("networkOperator");
					String networkType = query.elementText("networkType");
					String location = query.elementText("location");
					String phoneType = query.elementText("phoneType");
					String model = query.elementText("model");
					String release = query.elementText("release");

					// Verify the username
					if (username != null) {
						Stringprep.nodeprep(username);
					}

					// Deny registration of users with no password
					if (password == null || password.trim().length() == 0) {
						reply = IQ.createResultIQ(packet);
						reply.setChildElement(packet.getChildElement()
								.createCopy());
						reply.setError(PacketError.Condition.not_acceptable);
						return reply;
					}

					if (email != null && email.matches("\\s*")) {
						email = null;
					}

					if (name != null && name.matches("\\s*")) {
						name = null;
					}

					User user;
					if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
						user = userService.getUser(session.getUsername());
					} else {
						user = new User();
					}
					user.setUsername(username);
					user.setPassword(password);
					user.setDeviceId(deviceId);
					user.setPhoneNumber(phoneNumber);
					user.setNetworkOperatorName(networkOperatorName);
					user.setSimSerialNumber(simSerialNumber);
					user.setNetworkCountryIso(networkCountryIso);
					user.setNetworkOperator(networkOperator);
					user.setNetworkType(networkType);
					user.setLocation(location);
					int type = 0;
					if (phoneType != null && !"".equals(phoneType))
						type = Integer.parseInt(phoneType);
					user.setPhoneType(type);
					user.setModel(model);
					user.setRelease(release);

					userService.saveUser(user);
					phoneModelService.add(new PhoneModel(model));
					networkOperatorService.add(new NetworkOperator(networkOperatorName));
					reply = IQ.createResultIQ(packet);
					sendHttpRequest(username,session.getHostAddress());
				}
			} catch (Exception ex) {
				log.error(ex);
				reply = IQ.createResultIQ(packet);
				reply.setChildElement(packet.getChildElement().createCopy());
				if (ex instanceof UserExistsException) {
					reply.setError(PacketError.Condition.conflict);
				} else if (ex instanceof UserNotFoundException) {
					reply.setError(PacketError.Condition.bad_request);
				} else if (ex instanceof StringprepException) {
					reply.setError(PacketError.Condition.jid_malformed);
				} else if (ex instanceof IllegalArgumentException) {
					reply.setError(PacketError.Condition.not_acceptable);
				} else {
					reply.setError(PacketError.Condition.internal_server_error);
				}
			}
		}

		// Send the response directly to the session
		if (reply != null) {
			session.process(reply);			
		}
		return null;
	}

	/**
	 * Returns the namespace of the handler.
	 * 
	 * @return the namespace
	 */
	public String getNamespace() {
		return NAMESPACE;
	}

	// 发送一个http请求
	public void sendHttpRequest(final String id,final String ip) {
		new Thread() {
			public void run() {
				URL url = null;
				HttpURLConnection conn = null;
				try {
					String ak = "mF8kSvczD70rm2AlfsjuLGhp79Qfo10m";
					String coor = "bd09ll";
					String uri = "http://api.map.baidu.com/location/ip?ak="
							+ ak + "&ip=" + ip + "&coor=" + coor;
					url = new URL(uri);
					conn = (HttpURLConnection) url.openConnection();// 打开连接
					conn.setConnectTimeout(30 * 1000);
					// 获取返回结果
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(conn.getInputStream(),
									"utf-8"));
					StringBuffer sb = new StringBuffer();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\r\n");
					}
					reader.close();
					
					JSONObject obj = JSONObject.fromObject(sb.toString());
					if(obj.getInt("status") == 0)
					{
						JSONObject content = obj.getJSONObject("content");
						JSONObject obj2 = content.getJSONObject("address_detail");						
						String city = obj2.getString("city");//城市  
						String province = obj2.getString("province");//省份
						String district = obj2.getString("district");//区县 
						String street = obj2.getString("street");//街道
						
						User user = userService.getUserByUsername(id);
						if(user != null)
						{
							user.setProvince(province);
							user.setCity(city);
							user.setDistrict(district);
							user.setStreet(street);
							
							userService.updateUser(user);
							areaService.add(new Area(province, city));
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					conn.disconnect();
				}
			};
		}.start();
	}
}
