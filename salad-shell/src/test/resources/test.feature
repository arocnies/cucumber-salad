Feature: Test Shellcumber

  Scenario: Simple execution
    When I run: echo "hello world"
    Then the output should contain "hello world"

  Scenario: Loaded env from salad.yml
    When I run: env
    Then the output should contain "foo=bar"

  Scenario: Quoted simple execution
    When I run "ls -la"
    And I wait for the output to contain ".?" or timeout after 1 seconds
    Then the output should say "test.feature"
    But the output should not say "Foo bar"

  Scenario: Execute in a given folder
    Given I run "mkdir foo"
    And I run "cd foo"
    When I run "pwd"
    Then the output should say "resources/test/foo"

  Scenario: Command that generates a file
    Given the folder "/tmp/sandbox" exists
    When I run: echo "Y U NO RUN?" > /tmp/sandbox/meme.txt
    Then the file "/tmp/sandbox/meme.txt" should exist
    And the file "/tmp/sandbox/meme.txt" should contain "Y U NO"
    And the file "/tmp/sandbox/meme.txt" should not contain "my password"

  Scenario: Command that deletes a file
    Given the file "/tmp/sandbox/cl4p-tp.txt" exists
    When I run: rm "/tmp/sandbox/cl4p-tp.txt"
    Then the file "/tmp/sandbox/cl4p-tp.txt" should not exist

  Scenario: Command that deletes a folder
    And the folder "/tmp/test_folder" exists
    When I run: rmdir "/tmp/test_folder"
    Then the folder "/tmp/test_folder" should not exist

  Scenario: Checking for an empty directory
    Given the folder "/tmp/sandbox/empty" exists
    And the folder "/tmp/sandbox/empty" is not empty
    When I run: rm "/tmp/sandbox/empty/*"
    Then the folder "/tmp/sandbox/empty" should be empty

  Scenario: Checking for file presence
    Given the file "/tmp/sandbox/passwords.txt" does not exist
    When I run: cat /tmp/sandbox/passwords.txt
    Then the error output should read "No such file or directory"

  Scenario: Multi-shell
    When in "Shell-A", I run: echo Hello World
    And in "Shell-B", I run: echo Foo Bar
    Then in "Shell-A", the output should contain "Hello World"
    And in "Shell-A", the output should not contain "Foo Bar"
    And in "Shell-B", the output should contain "Foo Bar"
    And in "Shell-B", the output should not contain "Hello World"

  Scenario: One-off command output
    When I run: echo Hello World
    Then running "echo foobar" should not contain "Hello World"
    And running "echo foobar" should match "foobar"
    And the output should contain "Hello World"

  Scenario: Wait for command output
    Given the file "/tmp/test.txt" is empty
    And in "write-shell", I run "sleep 3; echo Hello File > /tmp/test.txt"
    And the file "/tmp/test.txt" should be empty
    When I wait for the command "cat /tmp/test.txt" to contain "Hello File", timeout after 10 seconds
    Then the file "/tmp/test.txt" should contain "Hello File"

  Scenario: Wait for shell output
    Given the file "/tmp/test.txt" is empty
    And in "write-shell", I run "sleep 3; echo Hello File > /tmp/test.txt"
    And the file "/tmp/test.txt" should be empty
    And in "read-shell", I run: while true; do cat /tmp/test.txt; sleep 0.5; done
    When in "read-shell", I wait for the output to say "Hello File", timeout after 10 seconds
    Then the file "/tmp/test.txt" should contain "Hello File"

  # This will always pass, we want to see it be interrupted.
  Scenario: Exit shell with never ending script
    Given in "forever-shell", I run: while true; do sleep 1; echo 1; done
