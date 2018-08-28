# Support-Rooster
Mock Support Rooster for GB Multimedia

## Release Notes
### V1.1
* Calendar now skips weekend from being included
* Fixed calendar not properly flushed when algorithm fails
* Fixed hour start on 00:00 rather than 12:00

### V1.0
* Initial commit
* List Engineer Screen:
  * Request engineer using mockup API
  * Inject data into recycler view adapter
* Generate Schedule Screen:
  * Generate schedule based on **rules** specified
  * Return schedule as WeekViewEvent to be printed in Android Week View layout
  
## Usages
* If fetching engineers failed, re-fetch by pulling the screen downwards.
* On Schedule screen, navigate and scroll to the right until you find the schedule (it begins on the first working day of upcoming week)

## Libraries Included
* Retrofit
* OkHttp
* RxJava (not implemented)
* RecyclerView
* Android Week View (https://github.com/alamkanak/Android-Week-View)
