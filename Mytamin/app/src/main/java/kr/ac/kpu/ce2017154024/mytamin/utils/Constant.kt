package kr.ac.kpu.ce2017154024.mytamin.utils

import kr.ac.kpu.ce2017154024.mytamin.R

object Constant {
    const val TAG : String = "로그"
    val explainTextStepOne = "   코로 천천히 숨을 들이마셨다가 \n배에 가득 담긴 공기를 잠시 느끼고\n    천천히 내뱉어 보세요."
    val explainTextStepTwo = "편한 자세로 눈을 감고 주변의 소리에\n 귀를 기울여보세요. 다음에는 코로\n 들어오는 주변의 냄새를 느껴보고\n손바닥에 느껴지는 감각에도 신경을\n             기울여봅시다."
    val StepTextstepOne = "숨고르기"
    val StepTextstepTwo = "감각 깨우기"

    val frametwo_image_one_src = R.drawable.icons8ogre96
    val frametwo_image_one_text ="매우 나빠요.."
    val frametwo_image_two_src = R.drawable.icons8angryface96
    val frametwo_image_two_text = "나쁜 편이에요:("
    val frametwo_image_three_src = R.drawable.icons8facewitrollingeyes96
    val frametwo_image_three_text = "그럭저럭이에요"
    val frametwo_image_four_src = R.drawable.icons8facewithhandovermouth96
    val frametwo_image_four_text = "좋은 편이에요:)"
    val frametwo_image_five_src = R.drawable.icons8grinningsquintingface96
    val frametwo_image_five_text = "매우 좋아요!"
}
object JOINSTRING{
    val wrongNickNameEmpty = "닉네임에 공백이 있습니다."
    val wrongNickNameoverlap = "이미 사용 중인 이메일입니다"
    val goodNickname = "사용 가능한 닉네임입니다"
    val start = "시작하기"
    val next="다음"
    val introduce_first_talk_one = "안녕하세요, "
    val introduce_first_talk_two = "님!\n매일 챙겨먹는 마음 비타민\n마이타민입니다!"
    val searchingEmail = "현재 이메일 중복검사 중입니다.."
    val searchingNickname = "현재 닉네임 중복검사 중입니다.."
}
object MYTAMIN{
    val step_one_title = "1. 숨고르기"
    val step_one_diagnosis = "   코로 천천히 숨을 들이마셨다가 \n배에 가득 담긴 공기를 잠시 느끼고\n       천천히 내뱉어 보세요."
    val step_two_title = "2. 감각 깨우기"
    val step_two_diagnosis = "편한 자세로 눈을 감고 주변의 소리에 귀를\n   기울여보세요. 다음에는 코로 들어오는\n주변의 냄새를 느껴보고 손바닥에 느껴지는\n           감각에도 신경을 기울여봅니다."
}
enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NO_CONTENT,
    USER_NOT_FOUND_ERROR,
    PASSWORD_PATTERN_ERROR

}