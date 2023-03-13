package io.mosip.ivv.e2e.methods;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import io.mosip.admin.fw.util.AdminTestException;
import io.mosip.admin.fw.util.TestCaseDTO;
import io.mosip.authentication.fw.precon.JsonPrecondtion;
import io.mosip.authentication.fw.util.AuthenticationTestException;
import io.mosip.ivv.core.base.StepInterface;
import io.mosip.ivv.core.exceptions.RigInternalError;
import io.mosip.ivv.orchestrator.BaseTestCaseUtil;
import io.mosip.service.BaseTestCase;
import io.mosip.testscripts.GetWithParam;
import io.mosip.testscripts.SimplePost;
import io.mosip.testscripts.SimplePostForAutoGenIdForUrlEncoded;
import io.restassured.response.Response;

public class UserInfo extends BaseTestCaseUtil implements StepInterface {
	static Logger logger = Logger.getLogger(UserInfo.class);
	private static final String AuthorizationCode = "idaData/AuthorizationCode/AuthorizationCode.yml";
	private static final String GenerateToken = "idaData/GenerateToken/GenerateToken.yml";
	private static final String GetUserInfo = "idaData/GetOidcUserInfo/GetOidcUserInfo.yml";
	SimplePost authorizationCode = new SimplePost();
	SimplePostForAutoGenIdForUrlEncoded generateToken = new SimplePostForAutoGenIdForUrlEncoded();
	GetWithParam getUserInfo = new GetWithParam();
	String clientId = "";
	String transactionId1 = "";
	String transactionId2 = "";
	String urlEncodedResp1 = "";
	String urlEncodedResp2 = "";
	String code = "";
	String redirectUri = "";
	String idpAccessToken = "";
	List<String> idType = BaseTestCase.getSupportedIdTypesValueFromActuator();

	@Override
	public void run() throws RigInternalError {

		Object[] testObjForAuthorizationCode = authorizationCode.getYmlTestData(AuthorizationCode);
		Object[] testObjForGenerateToken = generateToken.getYmlTestData(GenerateToken);
		Object[] testObjForGetUserInfo = generateToken.getYmlTestData(GetUserInfo);

		TestCaseDTO testAuthorization = (TestCaseDTO) testObjForAuthorizationCode[0];
		TestCaseDTO testGenerateToken = (TestCaseDTO) testObjForGenerateToken[0];
		TestCaseDTO testGetUserInfo = (TestCaseDTO) testObjForGetUserInfo[0];

		if (step.getParameters() == null || step.getParameters().isEmpty() || step.getParameters().size() < 1) {
			logger.error("transactionId parameter is  missing from DSL step");
			throw new RigInternalError("transactionId paramter is  missing in step: " + step.getName());
		} else {

			if (idType.contains("VID") || idType.contains("vid")) {
<<<<<<< HEAD
				transactionId2 = (String) step.getScenario().getOidcClientProp().get("transactionId2");
=======
				transactionId2 = (String) oidcClientProp.get("transactionId2");
				urlEncodedResp2 = (String) oidcClientProp.get("urlEncodedResp2");
>>>>>>> upstream/develop
				System.out.println(transactionId2);
			}

			else if (idType.contains("UIN") || idType.contains("uin")) {
<<<<<<< HEAD
				transactionId1 = (String) step.getScenario().getOidcClientProp().get("transactionId1");
=======
				transactionId1 = (String) oidcClientProp.get("transactionId1");
				urlEncodedResp1 = (String) oidcClientProp.get("urlEncodedResp1");
>>>>>>> upstream/develop
				System.out.println(transactionId1);
			}

			else {

				transactionId2 = (String) step.getScenario().getOidcClientProp().get("transactionId2");
				System.out.println(transactionId2);

			}

		}
		if (step.getParameters().size() == 2 || step.getParameters().get(1).startsWith("$$")) {
			if (step.getParameters().get(1).startsWith("$$")) {

				clientId = (String) step.getScenario().getOidcClientProp().get("clientId");

			}

			else {
				clientId = step.getParameters().get(1);
			}
		}

		// Auth Code API Call

		String inputForAuthorization = testAuthorization.getInput();

		if (idType.contains("VID") || idType.contains("vid")) {

			inputForAuthorization = JsonPrecondtion.parseAndReturnJsonContent(inputForAuthorization, transactionId2,
					"transactionId");

			inputForAuthorization = JsonPrecondtion.parseAndReturnJsonContent(inputForAuthorization,
					oidcClientProp.getProperty("urlEncodedResp2"), "encodedHash");

			testAuthorization.setInput(inputForAuthorization);

			try {
				authorizationCode.test(testAuthorization);

<<<<<<< HEAD
			Response response = authorizationCode.response;
			if (response != null) {
				JSONObject jsonResp = new JSONObject(response.getBody().asString()); // "$$transactionId=e2e_OAuthDetailsRequest($$clientId)"
				code = jsonResp.getJSONObject("response").getString("code");
				redirectUri = jsonResp.getJSONObject("response").getString("redirectUri");
				step.getScenario().getOidcClientProp().put("code", code);
=======
				Response response = authorizationCode.response;
				if (response != null) {
					JSONObject jsonResp = new JSONObject(response.getBody().asString()); // "$$transactionId=e2e_OAuthDetailsRequest($$clientId)"
					code = jsonResp.getJSONObject("response").getString("code");
					redirectUri = jsonResp.getJSONObject("response").getString("redirectUri");
					oidcClientProp.put("code", code);
>>>>>>> upstream/develop

				}

			} catch (AuthenticationTestException | AdminTestException e) {
				throw new RigInternalError(e.getMessage());

			}
		}

		else if (idType.contains("UIN") || idType.contains("uin")) {

			inputForAuthorization = JsonPrecondtion.parseAndReturnJsonContent(inputForAuthorization, transactionId1,
					"transactionId");
			inputForAuthorization = JsonPrecondtion.parseAndReturnJsonContent(inputForAuthorization,
					oidcClientProp.getProperty("urlEncodedResp1"), "encodedHash");

			testAuthorization.setInput(inputForAuthorization);

			try {
				authorizationCode.test(testAuthorization);

				Response response = authorizationCode.response;
				if (response != null) {
					JSONObject jsonResp = new JSONObject(response.getBody().asString()); // "$$transactionId=e2e_OAuthDetailsRequest($$clientId)"
					code = jsonResp.getJSONObject("response").getString("code");
					redirectUri = jsonResp.getJSONObject("response").getString("redirectUri");
					oidcClientProp.put("code", code);

				}

			} catch (AuthenticationTestException | AdminTestException e) {
				throw new RigInternalError(e.getMessage());

			}
		}

		//
		/*
		 * String inputForAuthorization = testAuthorization.getInput();
		 * inputForAuthorization =
		 * JsonPrecondtion.parseAndReturnJsonContent(inputForAuthorization,
		 * transactionId2, "transactionId");
		 * 
		 * testAuthorization.setInput(inputForAuthorization);
		 * 
		 * try { authorizationCode.test(testAuthorization);
		 * 
		 * Response response = authorizationCode.response; if (response != null) {
		 * JSONObject jsonResp = new JSONObject(response.getBody().asString()); //
		 * "$$transactionId=e2e_OAuthDetailsRequest($$clientId)" code =
		 * jsonResp.getJSONObject("response").getString("code"); redirectUri =
		 * jsonResp.getJSONObject("response").getString("redirectUri");
		 * oidcClientProp.put("code", code);
		 * 
		 * System.out.println(code);
		 * 
		 * System.out.println(jsonResp.toString()); }
		 * 
		 * } catch (AuthenticationTestException | AdminTestException e) { throw new
		 * RigInternalError(e.getMessage());
		 * 
		 * }
		 */

		String inputForGenerateToken = testGenerateToken.getInput();

		inputForGenerateToken = JsonPrecondtion.parseAndReturnJsonContent(inputForGenerateToken, clientId, "client_id");
		inputForGenerateToken = JsonPrecondtion.parseAndReturnJsonContent(inputForGenerateToken, code, "code");
		inputForGenerateToken = JsonPrecondtion.parseAndReturnJsonContent(inputForGenerateToken, redirectUri,
				"redirect_uri");

		testGenerateToken.setInput(inputForGenerateToken);

		try {
			generateToken.test(testGenerateToken);
		} catch (NoSuchAlgorithmException | AuthenticationTestException | AdminTestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Response response = generateToken.response;
		if (response != null) {
			JSONObject jsonResp = new JSONObject(response.getBody().asString());
			// idpAccessToken = jsonResp.getString("access_token");
			idpAccessToken = jsonResp.get("access_token").toString();
			System.out.println(jsonResp.toString());
		}

		// User Info API CALL //

		String inputForGetUserInfo = testGetUserInfo.getInput();

		inputForGetUserInfo = JsonPrecondtion.parseAndReturnJsonContent(inputForGenerateToken, idpAccessToken,
				"idpAccessToken");

		testGetUserInfo.setInput(inputForGetUserInfo);

		try {
			getUserInfo.test(testGetUserInfo);

			Response response2 = getUserInfo.response;
			System.out.println(response2.toString());

		} catch (AuthenticationTestException | AdminTestException e) {
			throw new RigInternalError(e.getMessage());

		}

	}

}
