package com.lpinc.testbed.controller;

import com.lpinc.testbed.simulator.agent.Agent;
import com.lpinc.testbed.simulator.runner.EstateRunner;
import com.lpinc.testbed.simulator.utils.Repeat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController extends BaseController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public Map<Agent, Map<String, Double>> home() {
      List<Double[]> lp = new ArrayList<>();
      List<Double[]> vs = new ArrayList<>();
      try {
        File file = new File("src/main/resources/example.csv");
        Scanner scanner = new Scanner(file);
        List<Double[]> list = lp;
        while (scanner.hasNextLine()) {
          String s = scanner.nextLine();
          if (s.startsWith("#tenant")) {
            list = vs;
          }
          if (!s.isBlank() && !s.startsWith("#")) {
            list.add(Arrays.stream(s.split(",")).map(Double::valueOf).toArray(Double[]::new));
          }
        }
        scanner.close();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      Double[][] landPrices = lp.toArray(new Double[0][]);
      Double[] tenantValues = vs.stream().map(l -> l[0]).toArray(Double[]::new);
      int a = Arrays.stream(landPrices).mapToInt(l -> l.length).reduce(0, Integer::sum);
      Integer[][] periods = new Integer[a][tenantValues.length];
      Repeat.fill(periods, i -> 12);

      EstateRunner simulator = new EstateRunner(landPrices, tenantValues, periods);
      Map<Agent, Double> map = simulator.run();
      return map.entrySet().stream().collect(Collectors.toMap(
          Entry::getKey, e -> {
            Map<String, Double> values = new HashMap<>();
            values.put("trust value", e.getValue());
            values.put("judgement value", e.getKey().getJudgement());
            return values;
          }));
    }

}
