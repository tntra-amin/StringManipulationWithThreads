package com.example.anotherDemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@SpringBootTest
class AnotherDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void monotrial(){

		Mono<String> m1 = Mono.just("M1");
		Mono<String> m2 = Mono.just("M2");
		Mono<String> m3 = Mono.just("M3");

		Mono<Tuple3<String, String, String>> m = Mono.zip(m1,m2,m3);

		m.subscribe( data -> {
			System.out.println(data.getT1());
		});

	}

}
