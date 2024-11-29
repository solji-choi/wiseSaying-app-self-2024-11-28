package com.ll;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final List<WiseSaying> wiseSayings;
    private int lastId;

    public App() {
        this.scanner = new Scanner(System.in);
        this.wiseSayings = new ArrayList<>();
        this.lastId = 0;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if(cmd.equals("종료")) break;
            else if(cmd.equals("등록")) {
                actionAdd();
            } else if(cmd.equals("목록")) {
                actionList();
            } else if(cmd.startsWith("삭제")) {
                actionDelete(cmd);
            } else if(cmd.startsWith("수정")) {
                actionModify(cmd);
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
        System.out.println(lastId + "번 명언이 등록되었습니다.");
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

    private boolean delete(String cmd) {
        return wiseSayings.removeIf(wiseSaying -> wiseSaying.getId() == getParamId(cmd));
    }

    private void actionDelete(String cmd) {
        if(getParamId(cmd) != 0) {
            if (delete(cmd)) System.out.println(getParamId(cmd) + "번 명언이 삭제되었습니다.");
            else System.out.println(getParamId(cmd) + "번 명언은 존재하지 않습니다.");
        } else {
            System.out.println("삭제할 id값을 입력해주세요.");
        }
    }

    private void actionModify(String cmd) {
        if(getParamId(cmd) != 0) {
            Optional<WiseSaying> opwiseSaying = wiseSayings.stream()
                    .filter(wiseSaying -> wiseSaying.getId() == getParamId(cmd))
                    .findFirst();

            if(!opwiseSaying.isEmpty()) {
                WiseSaying wiseSaying = opwiseSaying.get();

                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = scanner.nextLine();

                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = scanner.nextLine();

                wiseSaying.setContent(content);
                wiseSaying.setAuthor(author);
            } else {
                System.out.println(getParamId(cmd) + "번 명언은 존재하지 않습니다.");
            }
        } else {
            System.out.println("수정할 id값을 입력해주세요.");
        }
    }
}
