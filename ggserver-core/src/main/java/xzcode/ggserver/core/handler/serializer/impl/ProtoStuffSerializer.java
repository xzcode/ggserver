package xzcode.ggserver.core.handler.serializer.impl;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import xzcode.ggserver.core.handler.serializer.ISerializer;

/**
 * ProtoStuff序列化与反序列化工具
 * 
 * 
 * @author zai
 * 2019-09-09 10:51:42
 */
public class ProtoStuffSerializer implements ISerializer {

	public <T> byte[] serialize(T object) throws Exception {
		@SuppressWarnings("unchecked")
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(object.getClass());  
        return ProtostuffIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)); 
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
		T obj = clazz.newInstance();
		Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(clazz);  
		ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;  
	}
	
	public static void main(String[] args) throws Exception {
		ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();
		Player player = new Player();
		
		player.setNickname("James");
		player.setPoints(12);
		player.setWinner(true);
		player.setZhuang(false);
		player.setCoins(1000009);
		
		List<Card> cards = new ArrayList<>();
		
		Card card1 = new Card(11,1, new CardType(1));
		Card card2 = new Card(2,3, new CardType(2));
		Card card3 = new Card(8,4, new CardType(3));
		
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		
		player.setCards(cards);
		List<Integer> cardVals = new ArrayList<>();
		cardVals.add(101);
		cardVals.add(201);
		cardVals.add(301);
		player.setCardVals(cardVals);
		
		Card luckyCard = new Card(13,2);
		
		player.setLuckyCard(luckyCard);
		
		
		byte[] bytes = protoStuffSerializer.serialize(player);
		List<Byte> outputList = new ArrayList<>();
		for (Byte b : bytes) {
			outputList.add(b);
		}
		System.out.println(outputList);
		System.out.println(outputList.size());
		
		Player player2 = protoStuffSerializer.deserialize(bytes, Player.class);
		System.out.println(new Gson().toJson(player2));
		System.out.println(new Gson().toJson(player2).length());
	}
	

}
