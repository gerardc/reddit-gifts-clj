When participants sign up for a gift exchange on redditgifts they specify two properties that are used within our matching process:

1. Shipping Country (string)
2. Shipping Preferences - whether or not they are willing to ship internationally (boolean)
3. Your program needs to find the best possible way to match participants such that:

- Whenever possible, the participants shipping preferences are adhered to.
- Whenever possible, participants who send a gift to a country other than their own are more likely to receive a gift from a country other than their own. Participants who indicate that they are willing to ship internationally typically wish to receive internationally, so amongst those who are willing to ship internationally, maximize the number of people who receive a gift from a country other than their own.
- Whenever possible, participants should not receive a gift from the same person they send a gift to.
- Every participant must be matched.
- The matching process should runs efficiently, meaning the execution time is short.

Your program should take 1 parameter as input, a list (array) where each item is a list (array) that represents a participant and has these three elements:

1. unique id of participant (integer)
2. shipping country of participant (string)
3. willing to ship internationally (boolean)

Example: [[234, ‘US’, True], [235, ‘US’, False],]

Your program should return a list (array) where each element is a 2 item list (array) containing:
- Unique id of participant giving the gift
- Unique id of participant receiving the gift
- Example: [[234, 235], [235, 234],]

Your solution should be representative of how you program. You should be able to explain the program and understand its strengths and weaknesses. You may write this program in any language you’d like to, but it needs to be executable (no pseudo code please).

For your reference, here is a spreadsheet containing numbers from Secret Santa 2011 of participants from each country and their shipping preferences. You can use this to generate a dataset that you use for testing.

Send your solution and anything else you think might help show us how rad you are to jobs@redditgifts.com! We like github links, we love reading code, we LOVE seeing examples of previous work, especially if you tell us what you did and why it's a good (or bad) example. Please email us at jobs@redditgifts.com and we will get back to you!

Sincerely,

kickme444 and 5days
