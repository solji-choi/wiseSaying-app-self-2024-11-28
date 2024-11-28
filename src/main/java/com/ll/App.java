package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private List<WiseSaying> wiseSayings;
    private int lastId;

    public App() {
        scanner = new Scanner(System.in);
        wiseSayings = new ArrayList<>();
        lastId = 0;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while(true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if(cmd.equals("종료")) {
                break;
            } else if(cmd.equals("등록")) {
                actionAdd();
            } else if(cmd.equals("목록")) {
                actionList();
            } else if(cmd.startsWith("삭제")) {
                actionDelete(wiseSayings, cmd);
            } else if(cmd.startsWith("수정")) {
                actionModify(wiseSayings, cmd);
            }
        }
    }

    private void actionAdd() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        ++lastId;
        wiseSayings.add(new WiseSaying(lastId, content, author));

        System.out.println("%d번 명언이 등록되었습니다.".formatted(lastId));
    }

    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(WiseSaying wiseSaying : wiseSayings.reversed()){
            System.out.println("%d / %s / %s".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent()));
        }
    }

    private int getParamId(String cmd) {
        try {
            return Integer.parseInt(cmd.substring(6));
        } catch (Exception e) {
            return 0;
        }
    }

    private void actionDelete(List<WiseSaying> wiseSayings, String cmd) {
        if(getParamId(cmd) != 0){
            if(delete(wiseSayings, getParamId(cmd))) {
                System.out.println("%d번 명언이 삭제되었습니다.".formatted(getParamId(cmd)));
            } else {
                System.out.println("%d번 명언은 존재하지 않습니다.".formatted(getParamId(cmd)));
            }
        } else {
            System.out.println("삭제할 id값을 입력해주세요.");
        }
    }

    private boolean delete(List<WiseSaying> wiseSayings, int id) {
        return wiseSayings.removeIf(wiseSaying -> wiseSaying.getId() == id);
    }

    private void actionModify(List<WiseSaying> wiseSayings, String cmd) {
        boolean isModify = false;

        if(getParamId(cmd) != 0) {
            for(WiseSaying wiseSaying : wiseSayings.reversed()){
                if (wiseSaying.getId() == getParamId(cmd) ) {
                    System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
                    System.out.print("명언 : ");
                    String content = scanner.nextLine();

                    System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
                    System.out.print("작가 : ");
                    String author = scanner.nextLine();

                    wiseSaying.setContent(content);
                    wiseSaying.setAuthor(author);

                    isModify = true;
                }
            }

            if(!isModify) System.out.println("%d번 명언은 존재하지 않습니다.".formatted(getParamId(cmd)));
        } else {
            System.out.println("수정할 id값을 입력해주세요.");
        }
    }
}
