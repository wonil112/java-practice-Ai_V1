package A1;

import java.util.ArrayList;
import java.util.Scanner;

public class Ai_V3 {
  public static void main(String[] args) {
    //TODO:

    // 스캐너 및 기초변수 선언. number는 현재 4개의 입력이기에 설정한 변수.
    Scanner sc = new Scanner(System.in);
    int number = 4;
    String defendentVariableInIt = "";
    String indefendentVariableInIt = "";
    double defendentVariable = 0;
    double indefendentVariable = 0;
    int isValid = 0;
    // 프로그램 시작 문구 출력
    System.out.println("[인공지능 프로그램 V1]");
    System.out.println("=".repeat(30));

    // 종속변수 및 독립변수 입력받는 부분. 정해진 값을 입력하기 전까지 반복
    do {
      System.out.print("[System] 종속변수의 값을 입력하여 주세요 : ");
      defendentVariableInIt = sc.nextLine();
      if (!isValidDigitString(defendentVariableInIt) || defendentVariableInIt.isEmpty() || defendentVariableInIt.equals(".")) {
        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
      } else {
        defendentVariable = Double.parseDouble(defendentVariableInIt);
        System.out.print("[System] 독립변수의 값을 입력하여 주세요 : ");
        indefendentVariableInIt = sc.nextLine();
        if (!isValidDigitString(indefendentVariableInIt) || indefendentVariableInIt.isEmpty() || indefendentVariableInIt.equals(".")) {
          System.out.println("잘못된 입력입니다. 처음부터 다시 입력해주세요.");
        } else {
          indefendentVariable = Double.parseDouble(indefendentVariableInIt);
          isValid++;
        }
      }
    } while (isValid == 0);

    // 광고비 및 예측 방문자수 리스트 선언
    ArrayList<Double> visitNumber = new ArrayList<>();
    ArrayList<Double> advertisementRate = new ArrayList<>();
    String advertisementRateInIt = "";

    // 광고비 입력받는 부분.
    isValid = 0;
    for (int i = 0; i < number; i++) {
      do {
        System.out.print("[System] 예측에 영향을 주는 원인(광고비)의 값을 알려주세요. (단위: 원) : ");
        // 수식에 필요한 변수를 먼저 정의하여 줍니다. (※ 수식과 똑같은 변수명으로 정의 )
        advertisementRateInIt = sc.nextLine();
        if (!isValidDigitString(advertisementRateInIt) || advertisementRateInIt.isEmpty() || advertisementRateInIt.equals(".")) {
          System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        } else {
          advertisementRate.add(i, Double.parseDouble(advertisementRateInIt));
          System.out.printf("[안내] 입력된 %d번째 광고비는 %.1f원 입니다.%n", (i + 1), advertisementRate.get(i));
          isValid++;
        }
      } while (isValid == 0);
    }

    // 위에 선언한 방문자수 리스트에 수식 y = (x * a) + b 를 적용하기 위한 수식 부분 및 방문자 예측 출력부분
    visitNumber.add(0, (defendentVariable * advertisementRate.get(0)) + indefendentVariable);
    visitNumber.add(1, (defendentVariable * advertisementRate.get(1)) + indefendentVariable);
    visitNumber.add(2, (defendentVariable * advertisementRate.get(2)) + indefendentVariable);
    visitNumber.add(3, (defendentVariable * advertisementRate.get(3)) + indefendentVariable);
    System.out.println("[안내] AI의 '웹 페이지 방문자' 예측 결과는 다음과 같습니다.");
    for (int i = 0; i < visitNumber.size(); i++) {
      System.out.printf("%d 번째 예측) %.1f회 방문%n", (i + 1), visitNumber.get(i));
    }

    // 구분선
    System.out.print("\n");
    System.out.println("=".repeat(30));

    // 2차원 배열 생성
    double[][] xy_list = new double[visitNumber.size()][visitNumber.size()];
    String realVisitNumber = "";

    // 실제 웹 페이지 방문자 수를 입력받아 xy_list에 입력하는 부분.
    isValid = 0;
    for (int i = 0; i < visitNumber.size(); i++) {
      do {
        System.out.printf("[System] %d번째 실제 웹 페이지 방문자 수를 입력해주세요 : ", (i + 1));
        realVisitNumber = sc.nextLine();
        if (!isValidDigitString(realVisitNumber) || realVisitNumber.isEmpty() || realVisitNumber.equals(".")) {
          System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        } else {
          double realVisitNumberInIt = Double.parseDouble(realVisitNumber);
          for (int j = 0; j < 2; j++) {
            if (j == 0) {
              xy_list[i][j] = realVisitNumberInIt;
            } else {
              xy_list[i][j] = visitNumber.get(i);
            }
          }
          isValid++;
        }
      } while (isValid == 0);
    }

    // 데이터 출력 안내 문구 생성
    System.out.println("[안내] 인공지능 프로그램에 할당된 데이터\n");
    for (int i = 0; i < number; i++) {
      for (int j = 0; j < 2; j++) {
        if (j == 0) {
          System.out.printf("%d번째 데이터)%n[실제값] 웹 페이지 방문자수 : %.1f", (i + 1), xy_list[i][j]);
        } else {
          System.out.printf("  [인공지능 예측값] 웹 페이지 방문자수 : %.1f%n", xy_list[i][j]);
        }
      }
    }
    // 마지막 계산 안내문구 출력
    System.out.print("\n");
    System.out.println("=".repeat(30));
    System.out.println("[안내] 인공지능 프로그램의 성능 계산");
    System.out.println("=".repeat(30));

    // 실제값과 예측값의 오차, 제곱값 및 오차의 제곱 합 출력
    double sum_diff_pow = 0;
    for (int i = 0; i < number; i++) {
      for (int j = 0; j < 2; j++) {
        if (j == 0) {
          double diff = xy_list[i][j] - xy_list[i][j + 1];
          double diff_pow = diff * diff;
          sum_diff_pow += diff_pow;
          System.out.printf("[%d번째 데이터] 실제 값과 예측 값의 오차 : %.1f%n", (i + 1), diff);
          System.out.printf("[안내] 오차의 제곱 : %.1f%n", (diff_pow));
        }
      }
    }
    System.out.print("\n");
    System.out.println("=".repeat(30));
    System.out.println("[안내] 인공지능 프로그램의 성능(오차의 제곱 합) :" + sum_diff_pow);
  }

  // 유효성 검사 부분
  public static boolean isValidDigitString(String formula) {

    String digit = "1234567890.";
    int dotcount = 0;
    for (char c : formula.toCharArray()) {
      if (digit.indexOf(c) == -1) {
        return false;
      }
      if (c == '.') {
        dotcount++;
      }
    }
    return dotcount <= 1;
  }
}
