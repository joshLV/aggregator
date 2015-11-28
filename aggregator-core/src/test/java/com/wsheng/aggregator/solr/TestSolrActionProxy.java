/**
 * 
 */
package com.wsheng.aggregator.solr;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wsheng.aggregator.solr.bean.SolrBizField;
import com.wsheng.aggregator.solr.bean.SolrCore;
import com.wsheng.aggregator.solr.query.SolrQueryField;
import com.wsheng.aggregator.solr.query.SolrQueryField.SolrQueryFieldOperator;
import com.wsheng.aggregator.solr.query.SolrQueryField.SolrQueryFieldType;
import com.wsheng.aggregator.util.DateUtils;
import com.wsheng.aggregator.util.SolrUtils;



/**
 * ������������SolrJ��API��û���κε�Assert,����������������Զ�������ݱ�
 * 
 * Solr Facet search can reference 3 document:
 * 1) http://martin3000.iteye.com/blog/1330106
 * 
 * 2) http://macrochen.iteye.com/blog/1337576
 * 
 * 3) http://www.cnblogs.com/hoojo/archive/2011/10/21/2220431.html
 * 
 * Solr��������DocumentObjectBinder����SolrInputDocument �� User�����໥ת��
 * 
 * @author Josh Wang(Sheng)
 *
 * @email  josh_wang23@hotmail.com
 */
public class TestSolrActionProxy extends Assert   {

	private static SolrActionProxy solrActionProxy;
	
	
	@BeforeClass
	public static void init() {
		// solrActionProxy = new SolrActionProxy(SolrCore.FileImage);
		// solrActionProxy = new SolrActionProxy("http://192.168.1.157:8983/solr/universal");
		solrActionProxy = new SolrActionProxy("http://192.168.1.157:8080/solr/circle");
	}
	
	@Test
	public void add() throws UnsupportedEncodingException {
		// Get the origin doc if exists.
		SolrDocumentList docs = solrActionProxy.query("id:2").getResults();
		
		for (int i = 0; i < docs.size(); i++) {
			SolrDocument curDoc = docs.get(i);
			
			System.out.println(" Before Added -- " + SolrUtils.getValue(curDoc, "name"));
		}
		
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", 2); // ���������ͬid ��field������滻ԭ�е�field���൱��update������
		doc.addField("name", "Iphone6s");
		// doc.addField("manu", URLEncoder.encode("��������Ŷ������", "UTF-8"));
		doc.addField("manu", "����������ֻ�Ŷ������");
		doc.addField("popularity", 5);
		doc.addField(SolrBizField.last_update_time.name(), DateUtils.str2Date("2015-11-07 12:23:23", DateUtils.MIDDLE_LINE_TIMESTAMP));
		
		// doc.addField("test", "test");
		
		solrActionProxy.addDoc(doc);
		System.out.println(" Successfully add!!!");
		
		// Get the new created/updated doc .
		docs = solrActionProxy.query("id:2").getResults();
		
		for (int i = 0; i < docs.size(); i++) {
			SolrDocument curDoc = docs.get(i);
			
			// System.out.println(" After Added -- " + SolrUtils.getValue(curDoc, "name") + "  manu: " + URLDecoder.decode(SolrUtils.getValue(curDoc, "manu").toString(), "UTF-8"));
			System.out.println(" After Added -- " + SolrUtils.getValue(curDoc, "name") + "  manu: " + SolrUtils.getValue(curDoc, "manu") 
					+ " upateTime : " + DateUtils.str2Date2Str(SolrUtils.getValue(curDoc, "last_update_time").toString(), DateUtils.MIDDLE_LINE_TIMESTAMP)
					+ " nullField : " + SolrUtils.getValue(curDoc, "nullField"));
		}
	}
	
	@Test
	public void queryAll() {
		String query = "*:*";
		
		QueryResponse queryResponse = solrActionProxy.query(query);
		
		if (queryResponse != null) {
			SolrDocumentList docs = queryResponse.getResults();

			System.out.println("doc size: " + docs.size());
			
			for (SolrDocument doc : docs) {
				System.out.println(doc);
			}
		}
		
		
	}
	
	/**
	 * 1. ����Χ�����Return Test" ���Բ��Գɹ���������ط���
	 * ��Run As Junit Test�����ܳɹ���
	 * 
	 * 2. ����������Χ��VPN��������Connection Refused
	 * ������ �� ��Run As Junit Test�����ܳɹ� Ҳ��Connection Refused ?��
	 * 
	 * ���óɹ�log4j.properteis����Կ�����Ӧ��Exception������ʹ��c11seracher�������ԡ�
	 * 
	 */
	@Test
	public void queryById() {
		String query = "id:10684";
		SolrDocumentList docs = solrActionProxy.query(query).getResults();
		System.out.println("Number: " + " --- " + docs.getNumFound());
		for (SolrDocument doc : docs) {
			System.out.println(doc);
			
			Collection<String> fieldNames = doc.getFieldNames();
			for (String fieldName : fieldNames) {
				System.out.println(fieldName + " ------" + doc.getFieldValue(fieldName));
			}
			
		}
		
	}
	
	
	
	@Test
	public void remove() {
		solrActionProxy.removeDoc("100", true);
		
		SolrDocumentList docs = solrActionProxy.query("id:100").getResults();
		
		Assert.assertEquals(0, docs.size());
	}
	
	@Test
	public void addDocs() {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", 1);
		doc.addField("name", "HTC-TopOne");
		doc.addField("manu", "�ֻ��е�ս����");
		doc.addField("popularity", 9);
		// doc.addField("weight", 1.2);
		doc.addField(SolrBizField.last_update_time.name(), DateUtils.str2Date("2015-11-06 11:13:23", DateUtils.MIDDLE_LINE_TIMESTAMP));
		docs.add(doc);
		
		doc = new SolrInputDocument();
		doc.addField("id", 7);
		doc.addField("name", "Nokia Lumia 2000");
		doc.addField("manu", "΢���չ�ŵ�����ֻ���������������������ܶ�Ϊ�г�֮�");
		doc.addField("popularity", 10);
		doc.addField(SolrBizField.last_update_time.name(), DateUtils.str2Date("2015-11-08 9:13:23", DateUtils.MIDDLE_LINE_TIMESTAMP));
		// doc.addField("weight", 0.8);
		docs.add(doc);
		
		doc = new SolrInputDocument();
		doc.addField("id", 8);
		doc.addField("name", "HTC-TopOne");
		doc.addField("manu", "HTC2014���ֻ��������������ֹ���ֵ������, ���ڲ�������");
		doc.addField("popularity", 9);
		doc.addField(SolrBizField.last_update_time.name(), DateUtils.str2Date("2015-11-09 15:23:23", DateUtils.MIDDLE_LINE_TIMESTAMP));
		// doc.addField("weight", 1.2);
		docs.add(doc);
		
		
		
		solrActionProxy.addDocs(docs);
		
		// SolrJ API does not support this.
//		SolrDocumentList resultDocs = service.query("id: \"6 OR \"");
//		for (SolrDocument sd : resultDocs) {
//			System.out.println(sd);
//		}
		
	}

	
	@Test
	public void queryByManu() {
		String query = "manu:�ֻ�";
		// String query = "manu:��ɫ";
		query = "HTC2014���ֻ��������������ֹ���ֵ������, ���ڲ�������";
		SolrDocumentList docs = solrActionProxy.query(query).getResults();
		System.out.println(docs.getNumFound());
		for (int i = 0; i < docs.size(); i++) {
			System.out.println("Query By Manu:" + docs.get(i));
			
			SolrDocument doc = docs.get(i);
			
			System.out.println(" name value: " + SolrUtils.getValue(doc, "name"));
		}
		
		System.out.println( " ---- ");
		
		// get the values of the field 
		Set<Object> values = SolrUtils.getValues(docs, "name");
		
		if (values != null) {
			for (Object value : values) {
				System.out.println(value.toString());
			}
		}
	}
	
	@Test
	public void queryByParams() {
		// SolrQuery params = new SolrQuery("manu:���� AND name:Nokia");
		//SolrQuery params = new SolrQuery("manu:����");
		// params.setQuery("name:Nokia");
		
		// params.setQuery("name:HTC OR name:iphone");
		
		SolrQuery params = new SolrQuery("manu:�ֻ� OR (manu:����  AND manu:������)");
		
		System.out.println("query is: " + params.getQuery());
		SolrDocumentList docs = solrActionProxy.query(params).getResults();
		
		for (SolrDocument doc : docs) {
			System.out.println(doc);
		}
	}
	
	// TODO : debug facet and highlight functions, Did not debug them this time
	@Test
	public void dateRangeQuery() {
		List<SolrQueryField<?>> dateQueryFields = new ArrayList<SolrQueryField<?>>();
		
		List<String> dateValues1 = new ArrayList<String>();
		// dateValues1.add("2015-11-06T03:13:23Z");
		// dateValues1.add("2015-11-09T07:23:23Z");
		dateValues1.add("");
		dateValues1.add("");
		
		// http://blog.csdn.net/wangshfa/article/details/36367715
		// Spring provide the function to convert str to date 
		
		
		SolrQueryField<String> dateField = new SolrQueryField<String>(SolrBizField.last_update_time.name(), dateValues1, false, 
				false, true, ORDER.desc, SolrQueryFieldType.Date_Range, SolrQueryField.SolrQueryFieldOperator.OR); 
		
		dateQueryFields.add(dateField);
		
		List<QueryResponse> responses = solrActionProxy.query(dateQueryFields, 3, SolrQueryField.SolrQueryFieldOperator.OR);
		System.out.println(" === Start to print results ");
		for (QueryResponse response : responses) {
			SolrDocumentList docs = response.getResults();
			System.out.println("results : " + docs);
			
			for (SolrDocument doc : docs) {
				System.out.println(" doc : " + doc);
			}
		}
		
		System.out.println(" === End to print results ");
	}
	
	@Test
	public void numRangeQuery() {
		
	}
	
	@Test
	public void commonQuery() {
		List<SolrQueryField<?>> fields = new ArrayList<SolrQueryField<?>>();
		
		// The query will be parsed as: Nokia Lumia 2000,Iphone6s,HTC. And this equals "Nokia Lumia 2000 OR Iphone6s OR��HTC"
		List<String> values1 = new ArrayList<String>();
		values1.add("Nokia Lumia 2000");
		values1.add("Iphone6s");
		values1.add("HTC-TopOne");
		// values1.add("Nokia Lumia 2000,Iphone6s,HTC");
		// values1.add("Nokia Lumia 2000 OR Iphone6s OR��HTC");
		
		// multiple fields(strings) can't be sorted.
		// field1 is the second field - see below:fields.add(field1); facet, highlight, sort by name asc,
		SolrQueryField<String> field1 = new SolrQueryField<String>("name", values1, false, 
				false, false, null, SolrQueryFieldType.Common, SolrQueryField.SolrQueryFieldOperator.OR); 
		
		// field2 is the third field - see below:fields.add(field2);  facet, no highlight, no sort
		List<String> values2 = new ArrayList<String>();
		values2.add("���ֹ���");
		values2.add("��Ƶ");
		values2.add("������");
		SolrQueryField<String> field2 = new SolrQueryField<String>("manu", values2, false, false, true, ORDER.asc, 
				SolrQueryFieldType.Common, SolrQueryField.SolrQueryFieldOperator.OR);
		
		
		// field3 is the last field - see below: fields.add(field3);
		// no facet, no highlight, sort by id desc, id is String, so id "2" will ahead of id "100"
		// Note: the field connector of the last field must be null
		SolrQueryField<String> field3 = new SolrQueryField<String>("id", "3", false , true, true, ORDER.desc, 
				SolrQueryFieldType.Common, null);
		
		List<Integer> values4 = new ArrayList<Integer>();
//		values4.add(3000);
//		values4.add(5000);
		values4.add(1000);
		values4.add(null);
		
		// field4 is the first field - fields.add(field4); no facet, highlight, sort by price desc
		// Range field, sort by price desc firstly
		/*SolrQueryField<?> field4 = new SolrQueryField<Integer>("popularity", values4, false, false, true, ORDER.desc,
				SolrQueryFieldType.Num_Range, SolrQueryField.SolrQueryFieldOperator.OR);*/
	
	/*	List<Float> values4 = new ArrayList<>();
		values4.add(1.5f);
		values4.add(1.2f);
		// field4 is the first field - fields.add(field4); no facet, highlight, sort by price desc
		// Range field, sort by price desc firstly
		QueryField<?> field4 = new QueryField<Float>("weight", values4, false, false, true, ORDER.desc,
				FieldType.Normal_Range, QueryField.FieldConnector.OR);
	*/
		
		// date field
		List<String> dateValues1 = new ArrayList<String>();
		 dateValues1.add("2015-11-07T03:13:23Z");
		 dateValues1.add("2015-11-09T07:23:23Z");
		// dateValues1.add("");
		// dateValues1.add("");
		
		 // will use the "manu" fields order as a high priority
		SolrQueryField<String> dateField = new SolrQueryField<String>(SolrBizField.last_update_time.name(), dateValues1, false, 
				false, true, ORDER.asc, SolrQueryFieldType.Date_Range, SolrQueryField.SolrQueryFieldOperator.OR); 
		
		// fields.add(field4);
		fields.add(field1);
		fields.add(field2);
		//fields.add(field3);
		fields.add(dateField);
		
		List<QueryResponse> responses = solrActionProxy.query(fields, 30, SolrQueryFieldOperator.AND);
		
		System.out.println(" === Start to print results ");
		
		for (QueryResponse response : responses) {
			SolrDocumentList results = response.getResults();
			
			System.out.println(" total results size: " + results.size());
			
			for (SolrDocument doc : results) {
				System.out.println(doc);
			}
			
			System.out.println("======�����Ƭ��Ϣ=======");
			
			// �����Ƭ��Ϣ
			/*if (SolrUtils.getFacetField(response) != null) {
				for (FacetField facet : SolrUtils.getFacetField(response)) {
					System.out.println(" facet fieldName: " + facet.getName());
					List<Count> facetCounts = facet.getValues();
					for (FacetField.Count count : facetCounts) {
						System.out.println(count.getName() + " : " + count.getCount());
					}
				}
			}*/
			
			
			System.out.println("======���������field=======");
			/*Map<String, Map<String, List<String>>> hResults = SolrUtils.getHighlighFields(response);
			if (hResults != null) {
				for (Map.Entry<String, Map<String, List<String>>> doc : hResults.entrySet()) {
					System.out.println(" doc key(ID): " + doc.getKey() + " ---" + doc.getValue());
					for (Map.Entry<String, List<String>> hFields : doc.getValue().entrySet()) {
						System.out.println(" highlight field key: " + hFields.getKey());
						
						for (String field : hFields.getValue()) {
							System.out.println(" highlight field value: " + field);
						}
					}
					
				} 
			}*/
		}
		
		System.out.println(" === End to print results ");
		
		
		
	}
	
	
	@Test
	public void fullImport() {
		try {
			// full Import will re-index and replace the former ones
			new SolrHttpActionProxy(SolrCore.Diandi).fullImport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Must not execute this to clear the online data, the security action is comment this.
	 */
	/**@Test
	public void clearIndexs() {// Its safe to remove diandi core indexes.
		solrActionProxy.removeDocs("*:*", true);
	}*/
	
	
	@Test
	public void clearDismissedCircles() throws Exception {
		// can't access production db in local
//		List<CircleUserSummaryVO> circles = circleService.getCirclesByStatus(BaseStatus.Expired.getCode() + "");
//		for (CircleUserSummaryVO vo : circles) {
//			solrActionProxy.removeDoc(String.valueOf(vo.getCid()), true);
//		}
		
		// also can't access production solr server in local
		/*String filePath = "src/main/resources/dismissed_cids.txt";
		List<String> lines = IOUtils.readLines(filePath);
		for (String currentLine : lines) {
			System.out.println(currentLine);
			solrActionProxy.removeDoc(currentLine, true);
		}*/
		
	}
	
	
	@Test
	public void analysisDic() {
		//======== make sure init for Circle in init() method================
		// solrActionProxy = new SolrActionProxy(SolrCore.Circle);
		
		SolrDocumentList idDocs = solrActionProxy.query("id:77813").getResults();
		for (SolrDocument document : idDocs) {
			SolrInputDocument inputDocument = new SolrInputDocument();
			
			// ����maxword��˵��������һ����������Ҳ��������˵ı������������� ���롰�����������������Ѿ����ˣ�
			// �����ִʺ�Ľ���Ͳ�����������������������Ҳ����ֻ�������������߾�����ʱ��Ż�������
			String name = "�����˵ı���"; 
			String description = "�����ʱ���е���������ҿ�ʼ�������";
			
			description = "�������������"; // ʹ��maxword���ʿ��м�����������
			
			// complex�㷨���������ĵط����������������Ϊ��һ�������Ĵʡ�
		// 	description = "����ΪԱ�������˺ܶร��������Ա�����ԲμӸ����������Ϊ�����ṩר���Ŀռ�ȡ�";
			
			// �µ�document���滻�ɵ�document��id����Ҫ��ֵ����Ȼ������������
			inputDocument.setField(SolrBizField.id.name(), SolrUtils.getValue(document, SolrBizField.id.name()));
			inputDocument.setField(SolrBizField.description.name(), description);
			inputDocument.setField(SolrBizField.name.name(), name);
			solrActionProxy.addDoc(inputDocument);
		}
		
		idDocs = solrActionProxy.query("id:77813").getResults();
		for (SolrDocument document : idDocs) {
			System.err.println(" ---- After Update current id: " 
					+ SolrUtils.getValue(document, SolrBizField.id.name()) 
					+ " desc: " + SolrUtils.getValue(document, SolrBizField.description.name())
					+ " name: " + SolrUtils.getValue(document, SolrBizField.name.name()));
		}
	
	}
	
	@After
	public void clear() {
		solrActionProxy.releaseResource();
	}
	

}
