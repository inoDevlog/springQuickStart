package polymorphism;

public class AppleSpeaker implements Speaker {

	public AppleSpeaker() {
		System.out.println("===> AppleSpeaker ��ü ����");
	}

	public void volumeUp() {
		System.out.println("AppleSpeaker---UP");

	}

	public void volumeDowm() {
		System.out.println("AppleSpeaker---DOWN");

	}

}