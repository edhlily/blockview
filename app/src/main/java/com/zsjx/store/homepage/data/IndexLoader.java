package com.zsjx.store.homepage.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.config.BlockConfig;
import com.zsjx.store.homepage.IndexBlockConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.UUID;

import rx.Observable;

public class IndexLoader {
    private static IndexLoader indexLoader;

    JsonDeserializer jsonDeserializer = new JsonDeserializer<Block>() {

        @Override
        public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Block item = null;
            if (!json.isJsonNull() && json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                JsonElement element = jsonObject.get("type");
                System.out.println("======type:" + element.toString());
                if (element != null && !element.isJsonNull() && element.isJsonPrimitive()) {
                    String type = element.getAsString();
                    Class<? extends Block> tClass = mManager.getClass(type);
                    if (tClass != null) {
                        item = typeGson.fromJson(json, tClass);
                    }
                }
            }
            item.setUuid(UUID.randomUUID());
            return item;
        }
    };

    public IndexBlockConfig fromJson(String json) {
        return typeGson.fromJson(json, IndexBlockConfig.class);
    }

    public Gson typeGson = new GsonBuilder().registerTypeAdapter(Block.class, jsonDeserializer).disableHtmlEscaping().create();


    Context mContext;
    BlockManager mManager;
    String json;
    String mPath;

    public IndexLoader(Context context, BlockManager manager,String path) {
        mContext = context;
        mManager = manager;
        mPath = path;
        reset();
    }

    public void reset() {
        try {
            InputStreamReader isr = new InputStreamReader(mContext.getAssets().open(mPath), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            br.close();
            isr.close();
            json = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Observable<IndexBlockConfig> getIndexConfig(BlockManager manager) {
        try {
            return Observable.just(fromJson(json));
        } catch (Exception e) {
            return Observable.error(e);
        }
    }
}
