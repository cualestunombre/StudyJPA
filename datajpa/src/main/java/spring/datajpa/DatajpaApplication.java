package spring.datajpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.datajpa.entity.Member;
import spring.datajpa.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DatajpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatajpaApplication.class, args);
	}
	@Bean
	public AuditorAware<String> auditorAware(){
		return ()-> Optional.of(UUID.randomUUID().toString());
	}


	@Component
	@RequiredArgsConstructor
	static class AddMemberInAdvance{
		private final MemberRepository memberRepository;
		@Transactional
		@EventListener(ApplicationReadyEvent.class)
		public void addMember(){
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));
			memberRepository.save(new Member("1"));


		}
	}



}
