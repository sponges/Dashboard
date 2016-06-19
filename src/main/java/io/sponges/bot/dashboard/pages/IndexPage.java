package io.sponges.bot.dashboard.pages;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Page;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexPage extends Page {

    public IndexPage() {
        super("/", Method.GET, true);
    }

    @Override
    public Object execute(Request request, Response response, Model.Builder builder) {
        builder.with("name", "Fag lord 03");
        return new ModelAndView(builder.build(), "index.ftl");
    }
}